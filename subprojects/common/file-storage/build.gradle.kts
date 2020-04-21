plugins {
    id("kotlin")
    `maven-publish`
    id("com.jfrog.bintray")
}

dependencies {
    implementation(project(":common:time"))
    implementation(project(":common:logger"))

    implementation(Dependencies.retrofit)
    implementation(Dependencies.okhttp)
}
