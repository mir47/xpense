object Apps {
    const val minSdk = 23
    const val targetSdk = 33
    const val compileSdk = 33
    // todo: move version code and name from build.gradle.kts (app module)
    //  when fastlane increment_version_code and increment_version_name supports it
    // const val versionCode = 56
    // const val versionName = "0.54.0"
}

object Versions {
    const val gradle = "7.4.0"
    const val kotlin = "1.7.20"
    const val googleServices = "4.3.14"
    const val secretsGradlePlugin = "2.0.1"
    const val hilt = "2.44.2"
    const val navigation = "2.6.0-alpha04"
    const val androidxPreferenceKtx = "1.2.0"
    const val appcompat = "1.7.0-alpha01"
    const val composeBom = "2023.01.00"
    const val composeCompiler = "1.3.2"
//    const val composeMaterialThemeAdapter = "1.1.17"
    const val composeNavigation = "2.6.0-alpha04"
    const val constraintLayout = "2.2.0-alpha05"
    const val core = "1.10.0-alpha01"
    const val coroutine = "1.6.4"
    const val dexMaker = "2.28.1"
    const val firebase = "31.1.1"
    const val firebaseUiAuth = "8.0.2"
    const val fragment = "1.6.0-alpha04"
    const val lifecycle = "2.6.0-alpha04"
    const val maps = "18.1.0"
    const val material = "1.8.0-rc01"
    const val room = "2.5.0"
    const val timber = "5.0.1"

    /* test */
    const val junit = "4.13.2"
    const val archTesting = "2.2.0-alpha01"
    const val androidxTestCore = "1.5.0"
    const val androidxJunitKtx = "1.1.5"
    const val espresso = "3.5.1"
    const val robolectric = "4.9.2"
    const val mockito = "5.0.0"
}

object Libs {
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val secretsGradlePlugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.secretsGradlePlugin}"
    const val navigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val lifecycle = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val androidxPreferenceKtx = "androidx.preference:preference-ktx:${Versions.androidxPreferenceKtx}"
    const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val maps = "com.google.android.gms:play-services-maps:${Versions.maps}"

    // Firebase
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
    const val firebaseUiAuth = "com.firebaseui:firebase-ui-auth:${Versions.firebaseUiAuth}"

    // Compose
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composeMaterial = "androidx.compose.material:material"
    const val composeMaterialIcons = "androidx.compose.material:material-icons-extended"
    const val composeNav = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
    const val composeTools = "androidx.compose.ui:ui-tooling"
    const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    // When using a MDC theme
//    implementation("com.google.android.material:compose-theme-adapter:$version_compose_material_theme_adapter")
    // When using a AppCompat theme
//    implementation("com.google.accompanist:accompanist-appcompat-theme:0.16.0")
}

object TestLibs {
    // Unit Test
    const val junit = "junit:junit:${Versions.junit}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}"

    // AndroidX Test
    const val androidxArch = "androidx.arch.core:core-testing:${Versions.archTesting}"
    const val androidxCore = "androidx.test:core:${Versions.androidxTestCore}"
    const val androidxCoreKtx = "androidx.test:core-ktx:${Versions.androidxTestCore}"
    const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunitKtx}"
    const val androidxJunitKtx = "androidx.test.ext:junit-ktx:${Versions.androidxJunitKtx}"

    // Robolectric
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    // Mockito
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"

    // Espresso
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val idlingResource = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"

    // Fragments testing
    const val fragment = "androidx.fragment:fragment-testing:${Versions.fragment}"

    // Compose testing dependencies
    const val composeJunit = "androidx.compose.ui:ui-test-junit4"
    const val composeManifest = "androidx.compose.ui:ui-test-manifest"
    const val composeNav = "androidx.navigation:navigation-testing:${Versions.composeNavigation}"
}
