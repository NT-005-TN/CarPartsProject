#### Проект с MVP архитектурой приложения 

Используются Firebase, Room, Hilt, Coroutines, XML

Для подключения части интсрументов необходимо также ksp


### Трудности в подключении необходимого стека

## Не подзодит версия ksp
## Не верный синтаксис ввода

# Как правильно подключть все необходимое

В Module: app
~~~

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // FireBase
    id("com.google.gms.google-services")

    // ksp
    id("com.google.devtools.ksp")

    // Hilt
    id("com.google.dagger.hilt.android")
}

...

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // FireBase
    implementation("com.google.firebase:firebase-analytics")
    implementation(platform("com.google.firebase:firebase-bom:33.16.0"))

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // Room
    val roomVersion = "2.7.2"

    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:${roomVersion}")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.57")
    ksp("com.google.dagger:hilt-android-compiler:2.57")
}

~~~

В Project: CProject
~~~

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // FireBase
    id("com.google.gms.google-services") version "4.4.3" apply false

    // ksp
    id("com.google.devtools.ksp") version "2.2.0-2.0.2" apply false

    // Hilt
    id("com.google.dagger.hilt.android") version "2.56.2" apply false
}

~~~


### Проблемы с FireBase - получение SHA-1

## Как изменить в Android Studio версию Java с 8 до 11 и выше? 
Было трудно определить что же не нравилось терминалу при попытке сгенерировать SHA-1 для полноценной авторизации через Google

Терминал каждый раз писал:

~~~

FAILURE: Build failed with an exception.

* What went wrong:
A problem occurred configuring root project 'CProject'.
> Could not resolve all artifacts for configuration 'classpath'.
   > Could not resolve com.android.tools.build:gradle:8.11.1.
     Required by:
         root project : > com.android.application:com.android.application.gradle.plugin:8.11.1
      > Dependency requires at least JVM runtime version 11. This build uses a Java 8 JVM.
   > Could not resolve com.google.gms:google-services:4.4.3.
     Required by:
         root project : > com.google.gms.google-services:com.google.gms.google-services.gradle.plugin:4.4.3
      > Dependency requires at least JVM runtime version 11. This build uses a Java 8 JVM.
   > Could not resolve com.google.devtools.ksp:symbol-processing-gradle-plugin:2.2.0-2.0.2.
     Required by:
         root project : > com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:2.2.0-2.0.2
      > Dependency requires at least JVM runtime version 11. This build uses a Java 8 JVM.

* Try:
> Run this build using a Java 11 or newer JVM.
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.

Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.
For more on this, please refer to https://docs.gradle.org/8.13/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD FAILED in 27s
java version "1.8.0_51"
Java(TM) SE Runtime Environment (build 1.8.0_51-b16)
Java HotSpot(TM) 64-Bit Server VM (build 25.51-b03, mixed mode)

~~~

Здесь сказано о том, что необходима минимум версия Java 11, а на проекте стоит 8. 

Это было не так, там с самого начала была варсия JDK 22, поэтому было не понятно, в чем же загвоздка.
По итогу запустив команду с ключом отладки стало ясно, что именно терминал использует 8 версию Java.
~~~
./gradlew signingReport --info
~~~

Поэтому для продолжения работы над проектом временно версия Java была изменена в терминале до необходимой

~~~

$env:JAVA_HOME="C:\Program Files\Android\Android Studio\jbr"
$env:Path="$env:JAVA_HOME\bin;$env:Path"

~~~
