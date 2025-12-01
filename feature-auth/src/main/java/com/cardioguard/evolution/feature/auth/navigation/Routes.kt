package com.cardioguard.evolution.feature.auth.navigation

import android.net.Uri

sealed class Route(val path: String) {

    data object Login : Route("login")

    data object Register : Route("register") // ✅ Agregado

    data object Onboarding : Route("onboarding")

    // Dashboard con parámetro en la ruta
    data object Dashboard : Route("dashboard/{userName}") {
        fun create(userName: String): String =
            "dashboard/${Uri.encode(userName)}"
    }

    data object CardiacMonitor : Route("cardiac_monitor")

    data object AlertsCenter : Route("alerts_center")

    data object Profile : Route("profile")

    data object HistoryComplete : Route("history_complete")
}
