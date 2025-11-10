@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.library")              // 👈 sin alias, sin versión
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)     // 👈 Compose Compiler para Kotlin 2.0
}

android {
    namespace = "com.cardioguard.evolution.feature.history"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true                     // 👈 este módulo usa Compose (AlertsCenterScreen)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation("androidx.compose.material:material-icons-extended")

    // Compose UI
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Básicos
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}
