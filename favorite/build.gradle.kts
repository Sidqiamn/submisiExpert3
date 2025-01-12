plugins {
    alias(libs.plugins.android.dynamic.feature) // Jika menggunakan fitur dinamis
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.favorite"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
dependencies {
    implementation(project(":core"))
    implementation(project(":shared"))
    implementation(project(":app"))
    implementation("com.google.android.play:asset-delivery:2.2.2")

    // For Kotlin users also import the Kotlin extensions library for Play Asset Delivery:
    implementation("com.google.android.play:asset-delivery-ktx:2.2.2")
    implementation("com.google.android.play:feature-delivery:2.1.0")

    // For Kotlin users, also import the Kotlin extensions library for Play Feature Delivery:
    implementation("com.google.android.play:feature-delivery-ktx:2.1.0")
    implementation("com.google.android.play:review:2.0.2")

    // For Kotlin users, also import the Kotlin extensions library for Play In-App Review:
    implementation("com.google.android.play:review-ktx:2.0.2")
    implementation("com.google.android.play:app-update:2.1.0")

    // For Kotlin users, also import the Kotlin extensions library for Play In-App Update:
    implementation("com.google.android.play:app-update-ktx:2.1.0")
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.koin.android)

    implementation(libs.koin.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
