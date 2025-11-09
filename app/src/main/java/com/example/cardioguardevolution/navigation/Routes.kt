package com.example.cardioguardevolution.navigation

sealed class Route(val path: String) {
    data object Login : Route("login")
    data object Onboarding : Route("onboarding")
    data object Dashboard : Route("dashboard")
    // luego agregaremos más rutas (cardiac, mental, etc.)
}
