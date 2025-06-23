import org.gradle.kotlin.dsl.androidTestImplementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.jokesapi"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.jokesapi"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.jokesapi.CustomTestRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.fragmentKtx)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.daggerHiltAndroid)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.espresso.idling.resource)
    debugImplementation(libs.androidx.ui.tooling)
    ksp(libs.daggerHiltCompiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(platform(libs.network.retrofit.bom))
    implementation(libs.network.retrofit)
    implementation(libs.network.converterGson)
    implementation(libs.network.loggingInterceptor)
    implementation(libs.timber)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.image.glideCompose)

    implementation(libs.room.runtime)
    ksp(libs.room.kps.compiler)
    implementation(libs.room.ktx)

    testImplementation(libs.tests.junit)
    testImplementation(libs.tests.mockito.core)
    testImplementation(libs.tests.mockito.kotlin)
    testImplementation(libs.tests.coroutines)
    testImplementation(libs.tests.coroutines.turbine)

    androidTestImplementation(libs.tests.hilt.android.testing)
    kspAndroidTest(libs.daggerHiltCompiler)
    androidTestImplementation(libs.tests.compose.ui.junit4)
    debugImplementation(libs.tests.compose.ui.manifest)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.tests.hilt.android.testing)
    androidTestImplementation(libs.mockwebserver)

    androidTestImplementation("com.jakewharton.espresso:okhttp3-idling-resource:1.0.0")

}