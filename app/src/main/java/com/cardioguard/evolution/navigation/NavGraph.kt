package com.cardioguard.evolution.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cardioguard.evolution.feature.auth.navigation.Route
import com.cardioguard.evolution.feature.auth.ui.LoginScreen
import com.cardioguard.evolution.feature.auth.ui.OnboardingScreen
import com.cardioguard.evolution.feature.auth.ui.RegisterScreen
import com.cardioguard.evolution.feature.cardiac.ui.CardiacMonitorScreen
import com.cardioguard.evolution.feature.dashboard.ui.DashboardPrincipal
import com.cardioguard.evolution.feature.history.ui.AlertsCenterScreen
import com.cardioguard.evolution.feature.history.ui.HistoryCompleteScreen
import com.cardioguard.evolution.feature.profile.ui.ProfileScreen

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Route.Onboarding.path
    ) {

        // ONBOARDING
        composable(Route.Onboarding.path) {
            OnboardingScreen(navController = navController)
        }

        // LOGIN
        composable(Route.Login.path) {
            LoginScreen(
                onLoginSuccess = { name ->
                    navController.navigate(Route.Dashboard.create(name)) {
                        popUpTo(Route.Login.path) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onGoToOnboarding = {
                    navController.navigate(Route.Onboarding.path)
                }
            )
        }

        // REGISTER
        composable(Route.Register.path) {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onRegistered = { name ->
                    navController.navigate(Route.Dashboard.create(name)) {
                        popUpTo(Route.Register.path) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // DASHBOARD con argumento userName
        composable(
            route = Route.Dashboard.path,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName").orEmpty()
            DashboardPrincipal(
                userName = userName,
                onOpenMonitor = { navController.navigate(Route.CardiacMonitor.path) },
                onOpenAlerts = { navController.navigate(Route.AlertsCenter.path) },
                onOpenHistory = { navController.navigate(Route.HistoryComplete.path) },
                onOpenProfile = { navController.navigate(Route.Profile.path) }
            )
        }

        // MONITOR CARDÍACO
        composable(Route.CardiacMonitor.path) {
            CardiacMonitorScreen(
                onBack = { navController.popBackStack() },
                onShare = {},
                onExportPdf = {}
            )
        }

        // CENTRO DE ALERTAS
        composable(Route.AlertsCenter.path) {
            AlertsCenterScreen(onBack = { navController.popBackStack() })
        }

        // PERFIL
        composable(Route.Profile.path) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onOpenPrivacySettings = {}
            )
        }

        // HISTÓRICO COMPLETO
        composable(Route.HistoryComplete.path) {
            HistoryCompleteScreen(onBack = { navController.popBackStack() })
        }
    }
}
