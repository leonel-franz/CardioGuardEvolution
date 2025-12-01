pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CardioGuardEvolution"

include(":app")
include(":core-designsystem")
include(":core-model")
include(":core-common")
include(":core-data")

// Módulos feature
include(":feature-auth")
include(":feature-dashboard")
include(":feature-cardiac")
include(":feature-mental")
include(":feature-chat")
include(":feature-breathing")
include(":feature-history")
include(":feature-analysis")
include(":feature-contacts")
include(":feature-profile")
include(":feature-settings")
include(":feature-reports")

include(":core-models")

// Opcional: si más adelante quieres usar Dynamic Features, descomenta esta sección
// def dynamicFeatureModules = [":feature-auth", ":feature-dashboard", ":feature-cardiac"]
// project(":app").dynamicFeatures = dynamicFeatureModules
