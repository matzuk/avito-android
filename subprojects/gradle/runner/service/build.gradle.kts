plugins {
    id("kotlin")
    `maven-publish`
    id("com.jfrog.bintray")
}

extra["artifact-id"] = "runner-service"

dependencies {
    compileOnly(gradleApi())
    implementation(project(":gradle:runner:shared"))
    implementation(project(":gradle:utils"))
    implementation(Dependencies.funktionaleTry)
    implementation(Dependencies.androidTest.ddmlib)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.rxJava)

    testImplementation(project(":gradle:test-project"))
    testImplementation(project(":gradle:runner:shared-test"))
    testImplementation(Dependencies.kotlinReflect)
    testImplementation(Dependencies.test.truth)
    testImplementation(Dependencies.test.mockitoKotlin)
    testImplementation(Dependencies.test.mockitoJUnitJupiter)
}
