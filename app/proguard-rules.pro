-assumenosideeffects class **$$Lambda$* { *; }
 -assumenosideeffects class android.util.Log { *; }
-keep class org.slf4j.** { *; }
-dontwarn org.slf4j.**
-keep class tv.danmaku.ijk.media.player.** { *; }
-assumenosideeffects class kotlinx.coroutines.DebugStrings {
    public static *** toString(...);
}