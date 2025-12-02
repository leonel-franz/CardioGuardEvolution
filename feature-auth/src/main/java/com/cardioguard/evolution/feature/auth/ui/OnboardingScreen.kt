package com.cardioguard.evolution.feature.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cardioguard.evolution.feature.auth.navigation.Route
import com.cardioguard.evolution.feature.auth.R

@Composable
fun OnboardingScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo de imagen
        Image(
            painter = painterResource(id = R.drawable.bg_onboarding),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Capa con color y opacidad
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xAA10B3C4),
                            Color(0xAA10B3C4)
                        )
                    )
                )
        )

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Título / logo
            Text(
                text = "CardioGuardEvolution",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Spacer(Modifier.height(16.dp))

            // Botonera inferior
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = {
                        navController.navigate(Route.Login.path) {
                            popUpTo(Route.Onboarding.path) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Iniciar Sesión")
                }

                Spacer(Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {
                        navController.navigate(Route.Register.path) {
                            popUpTo(Route.Onboarding.path) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Registrarse")
                }
            }
        }
    }
}
