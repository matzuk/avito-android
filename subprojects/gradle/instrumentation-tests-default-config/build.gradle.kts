plugins {
    id("java-gradle-plugin")
    id("kotlin")
    `maven-publish`
    id("com.jfrog.bintray")
}

dependencies {
    api(project(":gradle:instrumentation-tests"))
    implementation(project(":gradle:kotlin-dsl-support"))
}

gradlePlugin {
    plugins {
        create("defaultConfig") {
            id = "com.avito.android.instrumentation-tests-default-config"
            implementationClass = "com.avito.instrumentation.InstrumentationDefaultConfigPlugin"
            displayName = "Instrumentation tests default configuration"
        }
    }
}
