package com.example.cardioguardevolution.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cardioguard.evolution.feature.auth.ui.LoginScreen
import com.cardioguard.evolution.feature.auth.ui.OnboardingScreen
import com.cardioguard.evolution.feature.cardiac.ui.CardiacMonitorScreen
import com.cardioguard.evolution.feature.dashboard.ui.DashboardPrincipal
import com.cardioguard.evolution.feature.history.ui.AlertsCenterScreen
import com.cardioguard.evolution.feature.history.ui.HistoryCompleteScreen
import com.cardioguard.evolution.feature.profile.ui.ProfileScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.path
    ) {

        // LOGIN
        composable(Route.Login.path) {
            LoginScreen(
                onLoginSuccess = { name ->
                    navController.navigate(Route.Dashboard.create(name)) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onGoToOnboarding = {
                    navController.navigate(Route.Onboarding.path)
                }
            )
        }

        // ONBOARDING
        composable(Route.Onboarding.path) {
            OnboardingScreen(
                onFinish = {
                    navController.popBackStack()
                }
            )
        }

        // DASHBOARD (con argumento userName en la ruta)
        composable(
            route = Route.Dashboard.path,
            arguments = listOf(
                navArgument("userName") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName").orEmpty()

            DashboardPrincipal(
                userName = userName,
                onOpenMonitor = {
                    navController.navigate(Route.CardiacMonitor.path)
                },
                onOpenAlerts = {
                    navController.navigate(Route.AlertsCenter.path)
                },
                onOpenHistory = {
                    navController.navigate(Route.HistoryComplete.path)
                },
                onOpenProfile = {
                    navController.navigate(Route.Profile.path)
                }
            )
        }

        // MONITOR CARDÍACO
        composable(Route.CardiacMonitor.path) {
            CardiacMonitorScreen(
                onBack = { navController.popBackStack() },
                onShare = { /* TODO: lógica de compartir */ },
                onExportPdf = { /* TODO: exportar PDF */ }
            )
        }

        // CENTRO DE ALERTAS
        composable(Route.AlertsCenter.path) {
            AlertsCenterScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // PERFIL
        composable(Route.Profile.path) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onOpenPrivacySettings = {
                    // Más adelante si tienes otra pantalla de settings, navegas aquí
                }
            )
        }

        // HISTÓRICO COMPLETO
        composable(Route.HistoryComplete.path) {
            HistoryCompleteScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
