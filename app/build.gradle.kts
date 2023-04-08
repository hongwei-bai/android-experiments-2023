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
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
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
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose}")

    // Below is from creating new project
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:${Versions.compose}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.compose}")
    implementation("androidx.compose.material3:material3:1.0.0-alpha11")
    implementation("com.airbnb.android:lottie-compose:6.0.0")
    implementation("com.airbnb.android:lottie:6.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Versions.compose}")
    debugImplementation("androidx.compose.ui:ui-tooling:${Versions.compose}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.compose}")
}