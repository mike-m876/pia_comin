# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in the SDK/tools/proguard/proguard-android.txt file.

# Keep Room entities
-keep class com.drinksafe.manager.data.models.** { *; }

# Keep ViewModel classes
-keep class com.drinksafe.manager.viewmodel.** { *; }

# Keep Navigation component
-keepnames class androidx.navigation.fragment.NavHostFragment
