# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /sdk/tools/proguard/proguard-android.txt

# Keep the service
-keep class com.babydream.whitenoise.AudioPlaybackService { *; }
-keep class com.babydream.whitenoise.NotificationReceiver { *; }

# Keep sound items
-keep class com.babydream.whitenoise.SoundItem { *; }