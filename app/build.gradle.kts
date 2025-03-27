import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
}


android {
    namespace = "com.danifitdev.citiesappchallenge"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.danifitdev.citiesappchallenge"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val properties = project.rootProject.file("local.properties")
        val mapsApiKey = if (properties.exists()) {
            Properties().apply { load(properties.inputStream()) }.getProperty("MAPS_API_KEY", "")
        } else {
            ""
        }
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        useJUnit()
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Jetpack Compose
    implementation(libs.ui)
    implementation(libs.androidx.material)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.activity.compose.v172) // Integración de Compose con Activity
    implementation(libs.coil.compose)

    // Navigation for Jetpack Compose
    //implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation(libs.androidx.navigation.compose)

    // Lifecycle, ViewModel, LiveData, Flow, y otros componentes
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx.v261)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Room Database
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)   // Soporte para corutinas y Flow en Room

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson) // Convertidor JSON usando Gson

    // OkHttp (Cliente HTTP para Retrofit)
    implementation(libs.logging.interceptor)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Test dependencies (opcional)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1")

    // JUnit para pruebas unitarias
    testImplementation(libs.junit)

// Mockito para mocks
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    // Dependencia para Mockito
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

// Coroutines para pruebas
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlinx.coroutines.test.v171)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)

    // Hilt Core
    implementation(libs.hilt.android.v250)
    kapt(libs.hilt.compiler)

    // Hilt con Jetpack Compose
    implementation(libs.androidx.hilt.navigation.compose.v120)

    // Si usas ViewModel en Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose.v262)

    //maps
    implementation(libs.google.maps.android.compose)

    //location
    implementation(libs.google.android.gms.play.services.location)

}