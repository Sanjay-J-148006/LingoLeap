# LingoLeap — Android Studio Setup & Run Guide

## Quick Start: Open & Run in Android Studio

### 1. Open the Project in Android Studio

1. **Open Android Studio** (if not already open).
2. **Click** `File` → `Open...`
3. **Navigate to:** `C:\Users\HP\StudioProjects\MyApplication\app\src\main\res\LingoLeap`
4. **Click** `OK` to open the project.
5. Android Studio will sync Gradle files automatically. **Wait for the Gradle sync to finish** (you'll see a progress bar at the bottom).

### 2. Set Up an Emulator or Connect a Device

**Option A: Use Android Studio's AVD Manager (Recommended)**
1. In Android Studio menu, click **Tools** → **Device Manager**
2. Click **Create Virtual Device** (if you don't have one) or **Select an existing AVD**
3. Click the **Play button** (▶) next to your AVD to launch it
4. Wait 30–90 seconds for the emulator to boot

**Option B: Connect a Physical Device**
1. **Enable USB Debugging** on your device (Settings → About Phone → tap Build Number 7 times → Developer Options → USB Debugging)
2. **Connect via USB** to your computer
3. A prompt will appear on your phone asking to allow debugging — **tap Allow**

### 3. Run the App

1. In Android Studio, click the **green Run button** (▶) in the top toolbar, or press **Shift + F10**
2. **Select your emulator or device** from the "Select Deployment Target" dialog
3. Click **OK**
4. Android Studio will:
   - Build the APK
   - Install it to the device/emulator
   - Launch the app automatically

### 4. View Output & Logs

- **Logcat** (bottom panel): Shows app console output, errors, and warnings
  - If not visible, click **View** → **Tool Windows** → **Logcat**
- **Run** (bottom panel): Shows build and install progress
- **Profiler** (available in Run menu): Monitor CPU, memory, and network usage in real-time

### Troubleshooting

**"No connected devices"**
- Verify the emulator is running (look for the emulator window)
- If using a physical device, check **Device Manager** or run in terminal:
  ```
  adb devices
  ```
  to see connected devices

**Build fails with "SDK path not found"**
- Click **File** → **Project Structure**
- Verify **SDK Location** points to `C:\Users\HP\AppData\Local\Android\Sdk`
- Click **Apply** and **OK**

**Emulator won't start**
- In Android Studio, click **Tools** → **SDK Manager**
- Ensure **Android 33 (API 33)** platform and **Google APIs x86_64 system image** are installed
- If not, install them and try again

**Gradle sync errors**
- Click **File** → **Sync Now** to manually trigger Gradle sync
- Or click **Offline Mode** toggle (bottom-right) to disable offline mode if stuck

## Project Structure

```
LingoLeap/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/lingoleap/
│   │   │   │   ├── LoginActivity.kt
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── LessonsActivity.kt
│   │   │   │   ├── QuizActivity.kt
│   │   │   │   ├── ProfileActivity.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── AppDatabase.kt (SQLite helper)
│   │   │   │   │   ├── FirebaseSync.kt (optional cloud sync)
│   │   │   │   │   └── User.kt
│   │   │   ├── res/
│   │   │   │   ├── layout/ (XML activity layouts)
│   │   │   │   ├── values/ (colors, strings)
│   │   │   │   └── drawable/ (icons/logo)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/ (unit tests)
│   │   └── androidTest/ (instrumentation tests)
│   └── build.gradle.kts (app dependencies and build config)
├── build.gradle.kts (root build config with Firebase plugin)
├── settings.gradle.kts
├── gradle/ (wrapper scripts)
└── local.properties (SDK path — auto-generated)
```

## Key App Features

- **Login Screen**: Demo credentials: `user@example.com` / `password123`
- **Dashboard (MainActivity)**: Shows XP, level, and quick stats
- **Lessons**: Select a language and difficulty level
- **Quiz**: Interactive questions with immediate feedback and XP awards
- **Profile**: View XP history, badges, and level
- **Local DB**: SQLite stores user progress, XP history, badges, and flashcards
- **Firebase Optional**: App gracefully falls back to local auth if Firebase is not configured

## Next Steps

1. **Open in Android Studio** (File → Open → select the LingoLeap folder)
2. **Wait for Gradle sync**
3. **Launch an emulator or connect a device**
4. **Click Run (▶)** and select your target
5. **Watch the app install and launch!**

---

**Questions or issues?** Check the Logcat panel in Android Studio for detailed error messages and debug output.
