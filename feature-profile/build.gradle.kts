@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.library")              // 👈 sin alias
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)     // 👈 Compose Compiler
}

android {
    namespace = "com.cardioguard.evolution.feature.profile"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true                     // 👈 ProfileScreen usa @Composable
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
    // Compose BOM (si ya lo tienes, déjalo igual)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // 👉 EN LUGAR de libs.androidx.compose.material.icons.extended
    // usa la dependencia directa:
    implementation("androidx.compose.material:material-icons-extended")

    // Básicos Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}