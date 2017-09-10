# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}


#-keep public class botsdk.touchkin.core.Touchkin { *; }
#-keep public class botsdk.touchkin.core.Touchkin$TYPE { *; }
#-keep public class botsdk.touchkin.core.Touchkin$BaseProperty { *; }
#-keep public class botsdk.touchkin.core.AuthCallBack { *; }


## GSON 2.2.4 specific rules ##

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

## for joda time
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }

#recycler view
-keep public class * extends android.support.v7.widget.RecyclerView$LayoutManager {
    public <init>(...);
}

-keep public class android.support.v7.widget.CardView {*;}




## Nineolddroid related classes to ignore

-keep class com.nineoldandroids.animation.** { *; }
-keep interface com.nineoldandroids.animation.** { *; }
-keep class com.nineoldandroids.view.** { *; }
-keep interface com.nineoldandroids.view.** { *; }


##for retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
#
##daimaja animations
-dontwarn com.daimajia.androidanimations.library.specials.**
-keep class com.daimajia.androidanimations.library.specials {*;}

## Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

##indicator library
#-keep class com.wang.avi.** { *; }
#-keep class com.wang.avi.indicators.** { *; }

##glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#okhttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

-dontwarn com.jeremyfeinstein.slidingmenu.lib.**
-dontwarn sun.misc.Unsafe

-keep class com.google.**
-dontwarn com.google.**
-keep class com.esotericsoftware.kryo.** {*;}


-keep class org.jsoup.**
-dontwarn org.jsoup.**
-dontwarn com.esotericsoftware.kryo.**
-keep class video.xdownloader.models.* {*;}
-dontwarn com.googlecode.mp4parser.**