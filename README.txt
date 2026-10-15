PELICAN LIVE ASSIST — CLOUD APK BUILD

This project includes a GitHub Actions workflow at .github/workflows/build-apk.yml.
It builds app/build/outputs/apk/debug/*.apk and uploads it as an Actions artifact.

Simplest setup:
1. Create a new GitHub repository named PelicanLiveAssist.
2. Upload the contents of this folder to the repository (not the outer ZIP).
3. Open the repository's Actions tab and select “Build Pelican APK”.
4. Tap “Run workflow” if it has not started automatically.
5. Open the completed run and download “PelicanLiveAssist-debug-apk” under Artifacts.
6. Extract the downloaded artifact ZIP to get app-debug.apk.

This is a source project and workflow, not an APK itself. The APK only exists after GitHub reports a successful build.
