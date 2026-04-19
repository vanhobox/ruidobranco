# ThaLemes - RuidoBranco

A white noise and soothing sounds app designed for newborns. Plays calming audio continuously even when the phone screen is locked.

## Features

- 8 looping white noise sounds (uterine, rain, ocean, waterfall, fan, humidifier, radio static, white noise)
- Background playback via Foreground Service
- Persistent notification with stop action
- WakeLock to prevent CPU from sleeping
- Dark lullaby theme with pulsing glow animations
- 2-column grid layout with tactile circular buttons

## Project Structure

```
app/
├── src/main/
│   ├── java/com/babydream/whitenoise/
│   │   ├── MainActivity.kt          # Compose UI & Service binding
│   │   ├── AudioPlaybackService.kt  # Foreground Service + MediaPlayer
│   │   ├── NotificationReceiver.kt  # Handles notification stop action
│   │   ├── SoundItem.kt             # Sound data class
│   │   └── ui/theme/                # Jetpack Compose theming
│   ├── res/
│   │   ├── raw/                     # Audio files (OGG/MP3)
│   │   ├── drawable/                # Vector icons
│   │   └── mipmap-*/                # Launcher icons
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Audio Files Required

Before building, add your audio files to `app/src/main/res/raw/`:

- `uterine.ogg`
- `rain.ogg`
- `ocean.ogg`
- `waterfall.ogg`
- `fan.ogg`
- `humidifier.ogg`
- `radio_static.ogg`
- `white_noise.ogg`

Use royalty-free looping tracks (30-60 seconds, seamless loops).

## Build

```bash
./gradlew assembleDebug
```

The APK will be at `app/build/outputs/apk/debug/app-debug.apk`

## Permissions

- `FOREGROUND_SERVICE` - Required for background audio
- `FOREGROUND_SERVICE_MEDIA_PLAYBACK` - For media playback service
- `WAKE_LOCK` - Keeps CPU alive during playback
- `POST_NOTIFICATIONS` - For Android 13+ notification permission# ruidobranco
