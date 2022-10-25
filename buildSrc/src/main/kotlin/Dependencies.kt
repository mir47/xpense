object Apps {
    const val minSdk = 23
    const val targetSdk = 33
    const val compileSdk = 33
    // todo: move versionCode and versionName from build.gradle.kts (app module)
    //  when fastlane increment_version_code and increment_version_name supports it
    // const val versionCode = 56
    // const val versionName = "0.54.0"
}

object Versions {
    const val gradle = "7.3.1"
    const val kotlin = "1.7.0"
    const val google_services = "4.3.14"
    const val secrets_gradle_plugin = "2.0.1"
    const val composeCompiler = "1.2.0"
    const val hilt = "2.44"
    const val navigation = "2.5.2"

    const val androidx_test_core = "1.4.0"
    const val androidx_test_ext_kotlin_runner = "1.1.3"
    const val androidx_preference_ktx = "1.2.0"
    const val appcompat = "1.5.1"
    const val arch_testing = "2.1.0"
    const val compose = "1.1.1"
    const val compose_compiler = "1.2.0"
    const val compose_material_theme_adapter = "1.1.17"
    const val compose_navigation = "2.5.0"
    const val compose_runtime_livedata = "1.0.0-beta01"
    const val compose_ui_tooling = "1.2.1"
    const val constraint_layout = "2.1.4"
    const val core = "1.9.0"
    const val coroutine = "1.6.4"
    const val dex_maker = "2.28.1"
    const val espresso = "3.4.0"
    const val firebase = "29.1.0"
    const val firebase_ui_auth = "7.2.0"
    const val fragment = "1.5.3"
    const val lifecycle = "2.6.0-alpha02"
    const val maps = "18.1.0"
    const val material = "1.6.1"
    const val mockito = "3.1.0"
    const val room = "2.4.3"
    const val robolectric = "4.7.3"
    const val timber = "5.0.1"

    /* test */
    const val junit = "4.13.2"
}

object Libs {
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val google_services = "com.google.gms:google-services:${Versions.google_services}"
    const val secrets_gradle_plugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.secrets_gradle_plugin}"
    const val navigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltKapt = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val lifecycle = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val androidx_preference_ktx = "androidx.preference:preference-ktx:${Versions.androidx_preference_ktx}"
    const val android_ktx = "androidx.core:core-ktx:${Versions.core}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Firebase
    const val firebaseUiAuth = "com.firebaseui:firebase-ui-auth:${Versions.firebase_ui_auth}"
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"

    // Compose
    // Compose Material Design
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    // Tooling support (Previews, etc.)
    const val composeTools = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    // Integration with ViewModels
    const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
    // When using a MDC theme
//    implementation("com.google.android.material:compose-theme-adapter:$version_compose_material_theme_adapter")
    // When using a AppCompat theme
//    implementation("com.google.accompanist:accompanist-appcompat-theme:0.16.0")
    // Material icons
    const val composeMaterialIcons = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val composeNav = "androidx.navigation:navigation-compose:${Versions.compose_navigation}"

    // Maps
    const val maps = "com.google.android.gms:play-services-maps:${Versions.maps}"
}

object TestLibs {
    // Unit Test
    const val junit = "junit:junit:${Versions.junit}"
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}"

    const val espressoIdlingResource = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"

    // AndroidX Test
    const val core = "androidx.arch.core:core-testing:${Versions.arch_testing}"
    const val coreKtx = "androidx.test:core-ktx:${Versions.androidx_test_core}"
    const val junitKtx = "androidx.test.ext:junit-ktx:${Versions.androidx_test_ext_kotlin_runner}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    // Android instrumented unit tests
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"

    const val coreAndroidX = "androidx.test:core:${Versions.androidx_test_core}"

    // AndroidX and Espresso Test
    const val junitAndroidX = "androidx.test.ext:junit:${Versions.androidx_test_ext_kotlin_runner}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"

    // Fragments testing
    const val fragment = "androidx.fragment:fragment-testing:${Versions.fragment}"

    // Compose testing dependencies
    const val compose = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    const val composeNav = "androidx.navigation:navigation-testing:${Versions.compose_navigation}"
    const val composeUi = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
}
