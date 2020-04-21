plugins {
    id("com.android.library")
    id("kotlin-android")
    id("digital.wup.android-maven-publish")
    `maven-publish`
    id("com.jfrog.bintray")
}

dependencies {
    implementation(Dependencies.androidAnnotations)
    implementation(Dependencies.test.junit)

    implementation(project(":android-lib:proxy-toast"))
    implementation(project(":android-test:ui-testing-core"))
    implementation(project(":android-test:junit-utils"))
}
