package com.cardioguard.evolution.feature.dashboard.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Usa el paquete donde tengas definidos los colores:
// si están en el módulo app:
import com.cardioguard.evolution.feature.dashboard.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardPrincipal(
    userName: String,
    onOpenMonitor: () -> Unit
) {
    val helloName = userName.ifBlank { "Usuario" }
    val timeNow = remember {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // <- para que se vea todo
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hola $helloName",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White
                        )
                    )
                    Text(
                        text = timeNow,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    )
                }
                FilledTonalIconButton(
                    onClick = { },
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = Color.White.copy(alpha = 0.1f),
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
                }
            }

            // TARJETA BPM
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBlanca      // blanco con ligera opacidad
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "72",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF222222)      // número oscuro
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "BPM",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF666666)   // gris oscuro
                                )
                            )
                        }

                        // Placeholder del grafiquito
                        Box(
                            modifier = Modifier
                                .height(24.dp)
                                .width(96.dp)
                        )
                    }

                    AssistChip(
                        onClick = {},
                        label = { Text("NORMAL") },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = VerdeNormal.copy(alpha = 0.12f),
                            labelColor = VerdeNormal
                        )
                    )
                }
            }

            // TARJETA ESTRÉS
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBlanca
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Estrés",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = Color(0xFF444444)
                            )
                        )
                        Text(
                            "40%",
                            color = NaranjaModerado,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    LinearProgressIndicator(
                        progress = { 0.40f },
                        modifier = Modifier.fillMaxWidth(),
                        color = NaranjaModerado,
                        trackColor = BarraFondo
                    )

                    AssistChip(
                        onClick = {},
                        label = { Text("MODERADO") },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = NaranjaModerado.copy(alpha = 0.12f),
                            labelColor = NaranjaModerado
                        )
                    )
                }
            }

            // TARJETA ANSIEDAD
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBlanca
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Ansiedad",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = Color(0xFF444444)
                            )
                        )
                        Text(
                            "15%",
                            color = VerdeNormal,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    LinearProgressIndicator(
                        progress = { 0.15f },
                        modifier = Modifier.fillMaxWidth(),
                        color = VerdeNormal,
                        trackColor = BarraFondo
                    )

                    AssistChip(
                        onClick = {},
                        label = { Text("BAJO") },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = VerdeNormal.copy(alpha = 0.12f),
                            labelColor = VerdeNormal
                        )
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            // ACCIONES RÁPIDAS (3 arriba, 2 abajo)
            FlowRow(
                // ⬇⬇ aquí la clave: centramos las filas y dejamos espacio entre botones
                horizontalArrangement = Arrangement.spacedBy(
                    12.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachRow = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                QuickAction(
                    icon = Icons.Outlined.FavoriteBorder,
                    text = "Monitoreo",
                    onClick = onOpenMonitor
                )
                QuickAction(
                    icon = Icons.Outlined.BarChart,
                    text = "Salud Mental",
                    onClick = { /* TODO */ }
                )
                QuickAction(
                    icon = Icons.Outlined.Alarm,
                    text = "Alertas",
                    onClick = { /* TODO */ }
                )
                QuickAction(
                    icon = Icons.Outlined.History,
                    text = "Historial",
                    onClick = { /* TODO */ }
                )
                QuickAction(
                    icon = Icons.Outlined.Person,
                    text = "Perfil",
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}

@Composable
private fun QuickAction(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(120.dp)            // más angosto para que quepan 3
            .height(90.dp),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color.White.copy(alpha = 0.20f)
        ),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.White.copy(alpha = 0.06f),
            contentColor = Color.White
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(Modifier.height(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
