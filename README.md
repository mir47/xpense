# Xpense

Xpense is an app to help keep track of your expenses and budget. It is currently in early development with more updates in the works.

This app integrates with Firebase service. To build the app, you need to link it to a Firebase project. You can create your own project on Firebase, and download the config file (google-services.json) to the `app` module root folder.

## Feature Wishlist:
  - migrate koin to hilt
  - jetpack compose
  - fastlane for build and deploy automation
  - github actions for automation
  - location tag on transactions
  - photo on transactions (e.g. receipt), maybe use AndroidX camera library
  - SMS receiver to create transactions automatically (requires permission policy on Play Console)
  - settings screen with SMS toggle
  - experiments screen (playground for new things)
  - ability to log transactions on different accounts e.g. credit, cheque, etc
  - login (optional), maybe use FirebaseUI
  - cloud data sync / backup (requires login)
  - Kotlin Mobile Multiplatform
  - Android watch app
  - equivalent iOS app
  - equivalent Huawei app
  - equivalent flutter app (exported to Android and iOS)
  - migrate room db to use kotlin flow
  - unit tests
  - instrumented tests
  - test coverage 
  - Proguard obfuscation
  - Support dark mode (current app uses default dark styling, which can be improved)
  - Jetpack DataStore (replaces shared prefs)
  - Lint

App is available for download on [Play Store](https://play.google.com/store/apps/details?id=com.xpense.android)
