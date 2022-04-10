fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## Android

### android test

```sh
[bundle exec] fastlane android test
```

Runs all the tests

### android beta

```sh
[bundle exec] fastlane android beta
```

Submit a new Beta Build to Crashlytics Beta

### android deploy

```sh
[bundle exec] fastlane android deploy
```

Deploy a new version to the Google Play

### android aab_play_store

```sh
[bundle exec] fastlane android aab_play_store
```

Build a signed release AAB and deploy to Google Play

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).




Fastlane setup errors:
[00:14:40]: Setting up `supply` (metadata management action) failed, but don't worry, you can try setting it up again using `fastlane supply init` whenever you want.
[00:14:40]: Installing dependencies for you...
...
[00:16:20]: ------------------------------
[00:16:20]: --- Where to go from here? ---
[00:16:20]: ------------------------------
[00:16:20]: 📸  Learn more about how to automatically generate localized Google Play screenshots:
[00:16:20]:             https://docs.fastlane.tools/getting-started/android/screenshots/
[00:16:20]: 👩‍✈️  Learn more about distribution to beta testing services:
[00:16:20]:             https://docs.fastlane.tools/getting-started/android/beta-deployment/
[00:16:20]: 🚀  Learn more about how to automate the Google Play release process:
[00:16:20]:             https://docs.fastlane.tools/getting-started/android/release-deployment/
[00:16:20]:
[00:16:20]: To try your new fastlane setup, just enter and run
[00:16:20]: $ fastlane test


