plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    kotlin("kapt")
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.imdumb"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.imdumb"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val tmdbApiKey = project.findProperty("TMDB_API_KEY") as? String ?: ""
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            val baseUrl = project.findProperty("BASE_URL_DEV") as? String ?: "https://api.themoviedb.org/3/"
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            manifestPlaceholders["appName"] = "Imdumb Dev"
        }
        create("prod") {
            dimension = "environment"
            applicationIdSuffix = ""
            val baseUrl = project.findProperty("BASE_URL_PROD") as? String ?: "https://api.themoviedb.org/3/"
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            manifestPlaceholders["appName"] = "Imdumb"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
        viewBinding = true
        buildConfig = true
    }

    variantFilter {
        val name = flavors[0].name
        val type = buildType.name
        // Solo permitimos devDebug y prodRelease
        if ((name == "dev" && type == "release") || (name == "prod" && type == "debug")) {
            ignore = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Retrofit + RxJava
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.adapter.rxjava2)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.rxkotlin)

    // Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database)
    implementation(libs.firebase.config)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
