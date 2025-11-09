package com.example.cardioguardevolution.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                onLoginSuccess = { name ->
                    val encoded = Uri.encode(name)
                    navController.navigate(Route.Dashboard.path + "?u=$encoded") {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onGoToOnboarding = {
                    navController.navigate(Route.Onboarding.path)
                }
            )
        }

        composable(Route.Onboarding.path) {
            OnboardingScreen(onFinish = { navController.popBackStack() })
        }

        // Dashboard con argumento opcional "u"
        composable(
            route = Route.Dashboard.path + "?u={u}",
            arguments = listOf(
                navArgument("u") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("u").orEmpty()
            DashboardPrincipal(
                userName = userName,
                onOpenMonitor = {
                    // navController.navigate("cardiac/monitor")  // luego
                }
            )
        }
    }
}
