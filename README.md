# ringojs-android
An example RingoJS on Android, RingoJS is a JavaScript platform base on
[Rhino](https://www.mozilla.org/rhino/).

This example use this [fork](https://github.com/kinpoo/ringojs) and
[rhino-android](https://github.com/F43nd1r/rhino-android) to dynamically
load classes on android/dalvik for RingoJS.

# ringojs-android library usage
https://www.jitpack.io/#kinpoo/ringojs-android

Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Step 2. Add it in your app build.gradle:
```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.github.kinpoo:ringojs-android:0.1'
    implementation 'org.mozilla:rhino:1.7.11'
    ...
}
```

Step 3. Download
[app/libs/ringo-core.jar](https://github.com/kinpoo/ringojs-android/blob/master/app/libs/ringo-core.jar?raw=true)
into your app/libs directory, download
[app/src/main/assets/ringo/ringo-modules.jar](https://github.com/kinpoo/ringojs-android/blob/master/app/src/main/assets/ringo/ringo-modules.jar?raw=true)
into your assets directory, or build them from this
[fork](https://github.com/kinpoo/ringojs).
