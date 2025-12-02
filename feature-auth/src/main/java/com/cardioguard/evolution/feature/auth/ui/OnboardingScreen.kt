package com.cardioguard.evolution.feature.auth.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cardioguard.evolution.feature.auth.R
import com.cardioguard.evolution.feature.auth.navigation.Route
import kotlinx.coroutines.delay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.cardioguard.evolution.design.components.TermsAndConditionsDialog

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingScreen(
    navController: NavController
) {
    // === Estados para diálogo de Términos ===
    var dialogOpen by remember { mutableStateOf(true) }

    TermsAndConditionsDialog(
        open = dialogOpen,
        onDismiss = { dialogOpen = false },
        onAccept = { dialogOpen = false }
    )

    // =====================
    // Slides del Onboarding
    // =====================
    val slides = listOf(
        SlideData(
            iconRes = R.drawable.ic_heartbeat,
            title = "Monitoreo Cardíaco",
            description = "Seguimiento en tiempo real de tu frecuencia cardíaca y salud cardiovascular",
            color = Color(0xFFE74C3C)
        ),
        SlideData(
            iconRes = R.drawable.ic_brain,
            title = "Salud Mental",
            description = "Técnicas de respiración guiada y apoyo para tu bienestar emocional",
            color = Color(0xFF2ECC71)
        ),
        SlideData(
            iconRes = R.drawable.ic_shield,
            title = "Alertas Inteligentes",
            description = "Sistema de detección que diferencia entre crisis cardíacas y ataques de pánico",
            color = Color(0xFFF39C12)
        )
    )

    var activeSlide by rememberSaveable { mutableStateOf(0) }

    // Auto-slide cada 4s
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000L)
            activeSlide = (activeSlide + 1) % slides.size
        }
    }

    // =====================
    // UI Principal
    // =====================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A2B4A), Color(0xFF0F1A2E))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(360.dp)
                .height(780.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1A2B4A), Color(0xFF0F1A2E))
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 24.dp, vertical = 20.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // === Logo ===
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF2ECC71), Color(0xFF27AE60))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_activity),
                            contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "CardioGuard",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Evolution",
                        fontSize = 18.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }

                // === Carrusel ===
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        targetState = activeSlide,
                        transitionSpec = {
                            val direction = if (targetState > initialState) 1 else -1
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> direction * fullWidth },
                                animationSpec = tween(450)
                            ) + fadeIn(animationSpec = tween(300)) with
                                    slideOutHorizontally(
                                        targetOffsetX = { fullWidth -> -direction * fullWidth },
                                        animationSpec = tween(450)
                                    ) + fadeOut(animationSpec = tween(200))
                        }
                    ) { targetIndex ->
                        val slide = slides[targetIndex]
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(CircleShape)
                                    .background(slide.color.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = slide.iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(70.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                slide.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                slide.description,
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.78f),
                                lineHeight = 20.sp,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                        }
                    }

                    // Indicadores
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        slides.forEachIndexed { index, _ ->
                            val isActive = index == activeSlide
                            val width: Dp by animateDpAsState(targetValue = if (isActive) 32.dp else 8.dp)
                            Box(
                                modifier = Modifier
                                    .height(8.dp)
                                    .width(width)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isActive) Color(0xFF2ECC71)
                                        else Color.White.copy(alpha = 0.18f)
                                    )
                                    .clickable { activeSlide = index }
                            )
                            if (index != slides.lastIndex) Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }

                // === Botones ===
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Route.Register.path) {
                                popUpTo(Route.Onboarding.path) { saveState = true }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2ECC71))
                    ) {
                        Text("Crear Cuenta", fontSize = 18.sp, color = Color.White)
                    }

                    Spacer(Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            navController.navigate(Route.Login.path) {
                                popUpTo(Route.Onboarding.path) { saveState = true }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(22.dp)
                    ) {
                        Text("Iniciar Sesión", fontSize = 18.sp, color = Color.White)
                    }

                    Spacer(Modifier.height(8.dp))

                    TextButton(onClick = { dialogOpen = true }) {
                        Text(
                            "Términos y Condiciones",
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}

// --- Helper data class ---
private data class SlideData(
    val iconRes: Int,
    val title: String,
    val description: String,
    val color: Color
)
