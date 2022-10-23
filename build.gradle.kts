buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.gradle)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.navigationSafeArgsGradlePlugin)
        classpath(Libs.google_services)
        classpath(Libs.secrets_gradle_plugin)
        classpath(Libs.hiltPlugin)
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
