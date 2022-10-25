import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.xpense.android"
    compileSdk = Apps.compileSdk

    defaultConfig {
        applicationId = "com.xpense.android"
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        // todo: move version code and name to Dependencies.Apps (buildSrc module)
        //  when fastlane increment_version_code and increment_version_name supports it
        versionCode = 58
        versionName = "0.56.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val key: String = gradleLocalProperties(rootDir).getProperty("MAPS_API_KEY")
        buildConfigField ("String", "MAPS_API_KEY", key)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // Set both the Java and Kotlin compilers to target Java 8.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    buildFeatures.apply {
        // Enable data binding
        dataBinding = true
        // Enables Jetpack Compose for this module
        compose = true
    }

    // Always show the result of every unit test when running via command line, even if it passes.
    testOptions {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
        animationsDisabled = true
    }

    packagingOptions {
        resources.excludes.addAll(listOf("META-INF/AL2.0", "META-INF/LGPL2.1"))
    }

    // Test source sets
//    sourceSets {
//        test.java.srcDirs += 'src/test/kotlin/'
//        androidTest.java.srcDirs += 'src/androidTest/kotlin/'
//    }
}

dependencies {
//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.appcompat)
    implementation(Libs.material)
    implementation(Libs.constraintLayout)
    implementation(Libs.fragment)
    implementation(Libs.lifecycle)
    implementation(Libs.androidxPreferenceKtx)

    // Android KTX
    implementation(Libs.androidxCoreKtx)

    // Room
    implementation(Libs.room)
    kapt(Libs.roomCompiler)

    // Kotlin Extensions and Coroutines support for Room
    implementation(Libs.roomKtx)

    // Coroutines
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)

    // Navigation
    implementation(Libs.navigationFragment)
    implementation(Libs.navigationUi)

    // Logging
    implementation(Libs.timber)

    // Firebase
    implementation(Libs.firebaseUiAuth)
    implementation(platform(Libs.firebaseBom))
    implementation(Libs.firebaseAnalytics)
    implementation(Libs.firebaseMessaging)
    implementation(Libs.firebaseAuth)
    // Add dependencies for other desired Firebase products:
    // https://firebase.google.com/docs/android/setup#available-libraries

    // Compose
    implementation(Libs.composeMaterial)
    implementation(Libs.composeTools)
    implementation(Libs.composeViewModel)
    implementation(Libs.composeMaterialIcons)
    implementation(Libs.composeNav)

    // Maps
    implementation(Libs.maps)

    // Hilt
    implementation(Libs.hilt)
    kapt(Libs.hiltKapt)

    implementation(TestLibs.idlingResource)

    // Unit Test
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.kotlin)
    testImplementation(TestLibs.coroutine)

    // AndroidX Test
    testImplementation(TestLibs.androidxArch)
    testImplementation(TestLibs.androidxCoreKtx)
    testImplementation(TestLibs.androidxJunitKtx)
    testImplementation(TestLibs.robolectric)

    // Android instrumented unit tests
    androidTestImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.kotlin)
    androidTestImplementation(TestLibs.mockitoCore)
    androidTestImplementation(TestLibs.mockitoAndroid)
//    androidTestImplementation "com.linkedin.dexmaker:dexmaker-mockito:$version_dex_maker"
    androidTestImplementation(TestLibs.coroutine)

    androidTestImplementation(TestLibs.androidxCoreKtx)
    androidTestImplementation(TestLibs.androidxCore)

    // AndroidX and Espresso Test
    androidTestImplementation(TestLibs.androidxJunit)
    androidTestImplementation(TestLibs.espressoCore)
    androidTestImplementation(TestLibs.espressoContrib)
    androidTestImplementation(TestLibs.androidxArch)

    // Fragments testing
    // Testing code should not be included in the main code.
    // Once https://issuetracker.google.com/128612536 is fixed this can be fixed.
    debugImplementation(TestLibs.fragment)
    implementation(TestLibs.androidxCore)

    // Compose testing dependencies
    androidTestImplementation(TestLibs.compose)
    androidTestImplementation(TestLibs.composeNav)
    debugImplementation(TestLibs.composeUi)
}
