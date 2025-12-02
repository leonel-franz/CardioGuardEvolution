package com.cardioguard.evolution.feature.dashboard.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cardioguard.evolution.feature.dashboard.vm.DashboardViewModel

@Composable
fun DashboardHost(
    userName: String,
    onNavigateToCardiacMonitor: () -> Unit,
    onOpenAlerts: () -> Unit,
    onOpenProfile: () -> Unit
) {
    val dashboardViewModel: DashboardViewModel = viewModel()

    DashboardPrincipal(
        dashboardViewModel = dashboardViewModel,
        userName = userName,
        onOpenMonitor = onNavigateToCardiacMonitor,
        onOpenAlerts = onOpenAlerts,
        onOpenProfile = onOpenProfile
    )
}
