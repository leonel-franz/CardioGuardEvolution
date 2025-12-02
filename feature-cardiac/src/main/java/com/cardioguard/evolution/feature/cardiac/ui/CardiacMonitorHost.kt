package com.cardioguard.evolution.feature.cardiac.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cardioguard.evolution.feature.dashboard.vm.DashboardViewModel

@Composable
fun CardiacMonitorHost(
    onBack: () -> Unit,
    onShare: () -> Unit,
    onExportPdf: () -> Unit
) {
    val dashboardViewModel: DashboardViewModel = viewModel()

    CardiacMonitorScreen(
        bpmHistory = dashboardViewModel.bpmHistory.value,
        onBack = onBack,
        onShare = onShare,
        onExportPdf = onExportPdf
    )
}
