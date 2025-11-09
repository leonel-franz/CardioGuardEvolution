package com.example.cardioguardevolution.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cardioguard.evolution.feature.auth.ui.LoginScreen
import com.cardioguard.evolution.feature.auth.ui.OnboardingScreen
import com.cardioguard.evolution.feature.dashboard.ui.DashboardPrincipal

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.path
    ) {
        composable(Route.Login.path) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Dashboard.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onGoToOnboarding = {
                    navController.navigate(Route.Onboarding.path)
                }
            )
        }

        composable(Route.Onboarding.path) {
            OnboardingScreen(
                onFinish = { navController.popBackStack() } // vuelve a Login
            )
        }

        composable(Route.Dashboard.path) {
            DashboardPrincipal(
                onOpenMonitor = {
                    // navController.navigate("cardiac/monitor") // lo agregamos después
                }
            )
        }
    }
}
