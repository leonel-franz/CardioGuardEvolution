package com.cardioguard.evolution.feature.dashboard.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardPrincipal(
    onOpenMonitor: () -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Dashboard", style = MaterialTheme.typography.headlineSmall)
            Text("BPM actual: —   Variabilidad: —")
            Button(
                onClick = onOpenMonitor,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Ir al monitor cardíaco") }
        }
    }
}
