buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
    }
}

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.newpokedexapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newpokedexapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            multiDexKeepProguard = file("multidex-config.pro")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    implementation("androidx.compose.ui:ui:1.6.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.4")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.multidex:multidex:2.0.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.4")

    debugImplementation("androidx.compose.ui:ui-tooling:1.6.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.4")

    implementation("androidx.compose.material:material-icons-core:1.6.4")
    implementation("androidx.compose.material:material-icons-extended:1.6.4")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("com.google.accompanist:accompanist-coil:0.15.0")



    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-android-compiler:2.51")
    implementation("com.google.dagger:dagger:2.42")
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")



    implementation ("androidx.palette:palette-ktx:1.0.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

}