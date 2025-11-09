package com.example.cardioguardevolution.navigation

import android.net.Uri

sealed class Route(val path: String) {
    data object Login : Route("login")
    data object Onboarding : Route("onboarding")
    data object Dashboard : Route("dashboard/{userName}") {
        fun create(userName: String): String =
            "dashboard/${Uri.encode(userName)}"
    }
}
