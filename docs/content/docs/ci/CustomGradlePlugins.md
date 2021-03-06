---
title: Custom Gradle Plugins
type: docs
---

# Custom Gradle plugins

Вся логика CI расположена в in-house Gradle плагинах. 
Для тестирования корневого проекта смотри модуль `build-script-test`.

## How to start

Начни с официальных туториалов, они сэкономят время:

- [Gradle plugin development tutorials](https://gradle.org/guides/?q=Plugin%20Development)   
Для нас не актуальна только публикация плагинов.
- [Custom tasks](https://docs.gradle.org/current/userguide/custom_tasks.html)

Если что-то не понятно, здесь тебе помогут:

- [#gradle (internal)](http://links.k.avito.ru/slackgradle)
- [gradle-community.slack.com](gradle-community.slack.com)

## Работа с плагинами в IDE

1. Предпочтительно использовать IntelliJ IDEA
1. Import project
1. Согласись использовать Gradle wrapper
1. **Settings > Build, Execution, Deployment > Build Tools > Gradle > Runner**
    1. Delegate IDE build/run actions to Gradle (check)
    1. Run tests using : Gradle Test Runner
    
Теперь можно запускать тесты по иконкам run

Known issues:

- Имя теста (DynamicTest.displayName) некорректно отображается в IDE: [#5975](https://github.com/gradle/gradle/issues/5975)

## Debugging

Для тестов работает из IDE.   
Для отладки плагина:

- Добавь в IDE конфигурацию Remote для запуска, как для обычного java проекта.
- Запускай Gradle из корня репозитория с параметрами `-Dorg.gradle.debug=true --no-daemon`

Debugger работает не только в нашем коде, остановиться можно и в AGP или Gradle.

## Testing Gradle plugins

### Isolating business-logic for unit-tests

You can isolate most of the logic from Gradle. Thus, it can be covered easily by unit-tests.

```kotlin
abstract class FeatureTask @Inject constructor(
    private val workerExecutor: WorkerExecutor
) : DefaultTask() {

    @TaskAction
    fun action() {
        val apiConfig = ... // get from the project
        workerExecutor.noIsolation().submit(FeatureWorkerAction::class.java) { parameters ->
            parameters.getIntegrationApiConfig().set(apiConfig)
        }
    }
}

// This wrapper is needed only for Worker API
// It can be started in another process. Thus, it has to prepare dependencies for the real work.

abstract class FeatureWorkerAction : WorkAction<Parameters> {

    interface Parameters : WorkParameters {
        fun getIntegrationApiConfig(): Property<IntegrationApiConfig>
    }

    override fun execute() {
        val api = IntegrationApiConfig.Impl(parameters.getIntegrationApiConfig().get())
        val action = FeatureAction(
            integrationApi = api
        )
        action.execute()
    }
}

// This class is responsible for the real work.
// The less it knows about Gradle, the better.

class FeatureAction(
    private val integrationApi: IntegrationApi
) {
    fun execute() {
        // Do the real work here
    }
}

// Now you can use simple mocks to test the action.
@Test
fun test() {
    val integrationApi = mock<IntegrationApi>()
    whenever(integrationApi.foo).thenReturn(bar())
    
    val action = FeatureAction(integrationApi) <-- No Gradle abstractions here
    action.execute()
    
    assertThat(...)
}
```

### Integration tests

For simple cases you can create dummy instance of Project by [ProjectBuilder](https://docs.gradle.org/current/javadoc/org/gradle/testfixtures/ProjectBuilder.html)

```kotlin
val project = ProjectBuilder.builder().build()

val task = project.tasks.register<TestTask>("testTask") {}

task.get().doStuff()
```

When you need to run a real build, use [Gradle Test Kit](https://docs.gradle.org/current/userguide/test_kit.html).\
See ready utilities in `:test-project` module.


### Запуск тестов из консоли

`./gradlew test`

Чтобы не останавливать прогон тестов на первом падении добавь `--continue`

Для запуска отдельного теста, класса или пакета работает фильтр: `--tests package.class.method`, 
но нужно запускать тесты для отдельного модуля, иначе фильтр упадет не найдя нужных тестов по фильтру 
в первом попавшемся модуле.

## Best practices

### Fail-fast contract

Each plugin should check preconditions _as early as possible_. 
If some parameter is missing or has invalid value, the plugin should fail and explain the reason.

### Feature toggles

Плагин может сломаться и заблокировать всем работу с проектом. 
Чтобы дать себе время на исправление, делай плагин отключаемым:

```kotlin
open class MyPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // В каждом property используем префикс `avito.<plugin>`
        // сразу видно где используется
        if (!project.getBooleanProperty("avito.my_plugin.enabled", default = false)) {
            project.logger.lifecycle("My plugin is disabled")
            return
        }
```

Тогда каждый разработчик сможет локально отключить плагин в случае проблем. 

## Директория ci

Там храним всю интеграцию с CI.   
Часто нужно править плагин совместно с ./ci/

Чтобы работать одновременно со всем этим кодом, к уже открытому проекту
добавь модуль ci: **File > New > Module from existing sources > путь до папки ci > ok > ok**

## Интеграция плагина в CI

[CI Gradle Plugin]({{< ref "/docs/ci/CIGradlePlugin.md" >}})

## Дополнительные материалы

- [Интеграция с AGP 4+](https://youtu.be/OTANozHzgPc)
