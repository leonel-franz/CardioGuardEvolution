package com.cardioguard.evolution.feature.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Bienvenido a CardioGuard", style = MaterialTheme.typography.headlineSmall)
            Text("Monitorea tu corazón y salud mental en un solo lugar.")
            Button(
                onClick = onFinish,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Continuar") }
        }
    }
}
