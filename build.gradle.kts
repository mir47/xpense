plugins {
    // plugin to check for updates to dependencies and Gradle itself.
    // source: https://github.com/ben-manes/gradle-versions-plugin
    // run: ./gradlew dependencyUpdates
    id("com.github.ben-manes.versions") version "0.28.0"
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.gradle)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.navigationSafeArgsGradlePlugin)
        classpath(Libs.googleServices)
        classpath(Libs.secretsGradlePlugin)
        classpath(Libs.hiltPlugin)
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
