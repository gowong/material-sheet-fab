# Release procedure
## Library
1. Increment `libraryVersion` in `material-sheet-fab/build.gradle` and gradle snippet in `README.md`.
1. Update changelog.
1. Fill in bintray credentials in `local.properties`
1. From terminal in Android Studio:
	1. `gradlew install`
	1. `gradlew bintrayUpload`
1. Remove credentials from `local.properties`
1. Verify that new version was uploaded to [Bintray](https://bintray.com/gowong/maven/material-sheet-fab/view#files).
1. Sync to Maven:
	1. Go to [Bintray](https://bintray.com/gowong/maven/material-sheet-fab/view#central).
	1. Enter Sonatype credentials.
	1. Click Sync.
1. Commit release changes. Tag release commit.

## Sample app
1. Increment `versionName` and `versionCode` in `sample/build.gradle`.
1. Build signed APKs using Android Studio `Build > Generate Signed APK...`.
1. Test APKs when upgrading and installing for the first time.
1. Upload APKs to Google Play.
1. Update Google Play what is new and app description (if needed).
1. Take new screen shots and upload to Google Play (if needed).
1. Commit release changes and APK.