import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.exp23"
    compileSdk = 33

    val staticApiToken = gradleLocalProperties(rootDir).getProperty("staticApi.token") ?: "\"ci\""
    buildTypes {
        getByName("debug") { buildConfigField("String", "staticApiToken", staticApiToken) }
        getByName("release") { buildConfigField("String", "staticApiToken", staticApiToken) }
    }

    val paymentApiTokenDebug =
        gradleLocalProperties(rootDir).getProperty("squareToken.token.debug") ?: "\"ci\""
    val paymentApiTokenProd =
        gradleLocalProperties(rootDir).getProperty("squareToken.token.prod") ?: "\"ci\""
    buildTypes {
        getByName("debug") { buildConfigField("String", "paymentApiToken", paymentApiTokenDebug) }
        getByName("release") { buildConfigField("String", "paymentApiToken", paymentApiTokenProd) }
    }

    defaultConfig {
        applicationId = "com.example.exp23"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtension
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }

    signingConfigs {
        named("debug") {
            keyAlias = "appKey"
            keyPassword = "appKey"
            storeFile = file("../exp23.jks")
            storePassword = "appKey"
        }
        create("release") {
            keyAlias = "appKey"
            keyPassword = "appKey"
            storeFile = file("../exp23.jks")
            storePassword = "appKey"
        }
    }
}

dependencies {
    // Below is from Google sample project Crane
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.Kotlin.Coroutines.core)
    implementation(Libs.Kotlin.Coroutines.android)
    implementation(Libs.AndroidX.Lifecycle.viewModelCompose)
    implementation(Libs.AndroidX.Lifecycle.viewModelKtx)
    implementation(Libs.Hilt.android)
    kapt(Libs.Hilt.compiler)

    // Add for Exp23
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.google.accompanist:accompanist-systemuicontroller:${Versions.composeAccompanist}")
    implementation("androidx.navigation:navigation-compose:${Versions.navigationCompose}")
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose}")

    // Payment
//    implementation("com.squareup.sdk.in-app-payments:card-entry:1.6.2")
//    implementation("com.squareup.sdk.in-app-payments:google-pay:1.6.2")
//    implementation("com.stripe:stripe-android:20.21.1")
//    implementation("com.paypal.checkout:android-sdk:0.8.8")
    implementation("com.google.android.gms:play-services-wallet:${Versions.googleWallet}")
    implementation("com.google.android.gms:play-services-pay:${Versions.googlePay}")
    implementation(Libs.AndroidX.appcompat)

    // Below is from creating new project
    implementation("androidx.core:core-ktx:${Versions.kotlin}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}")
    implementation("androidx.activity:activity-compose:${Versions.activityCompose}")
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose}")
    implementation("androidx.compose.material3:material3:1.1.0-beta02")
    implementation("com.airbnb.android:lottie-compose:${Versions.lottieCompose}")
    implementation("com.airbnb.android:lottie:${Versions.lottieCompose}")
    implementation("androidx.hilt:hilt-navigation-compose:${Versions.hilt}")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.compose}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.compose}")
}