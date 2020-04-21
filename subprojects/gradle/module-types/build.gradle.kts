plugins {
    id("kotlin")
    id("java-gradle-plugin")
    `maven-publish`
    id("com.jfrog.bintray")
}

dependencies {
    implementation(project(":gradle:impact-shared"))
    implementation(project(":gradle:utils"))
    implementation(project(":gradle:pre-build"))
    implementation(project(":gradle:kotlin-dsl-support"))
    implementation(project(":gradle:impact"))
    implementation(Dependencies.gradle.kotlinPlugin)

    testImplementation(project(":gradle:test-project"))
}

gradlePlugin {
    plugins {
        create("moduleTypes") {
            id = "com.avito.android.module-types"
            implementationClass = "com.avito.android.ModuleTypesPlugin"
            displayName = "Module types"
        }
    }
}
