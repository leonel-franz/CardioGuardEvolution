package com.cardioguard.evolution.feature.dashboard.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.DeviceThermostat
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Watch
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cardioguard.evolution.feature.dashboard.ui.theme.BarraFondo
import com.cardioguard.evolution.feature.dashboard.ui.theme.CardBlanca
import com.cardioguard.evolution.feature.dashboard.ui.theme.NaranjaModerado
import com.cardioguard.evolution.feature.dashboard.ui.theme.RojoAlerta
import com.cardioguard.evolution.feature.dashboard.ui.theme.VerdeNormal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.cardioguard.evolution.feature.dashboard.vm.DashboardViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardPrincipal(
    dashboardViewModel: DashboardViewModel,
    userName: String,
    onOpenMonitor: () -> Unit,
    onOpenAlerts: () -> Unit,
    onOpenHistory: () -> Unit = {},
    onOpenProfile: () -> Unit = {}
) {
    val helloName = userName.ifBlank { "Usuario" }
    val timeNow = remember { SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // HEADER CON ICONOS DE ESTADO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hola $helloName",
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )
                    Text(
                        text = timeNow,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Estado WiFi dinámico
                    Box {
                        Icon(
                            imageVector = Icons.Outlined.Wifi,
                            contentDescription = "Estado WiFi",
                            tint = if (dashboardViewModel.wifiConnected.value) VerdeNormal else RojoAlerta,
                            modifier = Modifier.size(20.dp)
                        )
                        if (!dashboardViewModel.wifiConnected.value) {
                            Surface(
                                modifier = Modifier.size(8.dp).align(Alignment.TopEnd),
                                shape = CircleShape,
                                color = RojoAlerta
                            ) {}
                        }
                    }

                    // Estado Sensor dinámico
                    Box {
                        Icon(
                            imageVector = Icons.Outlined.Watch,
                            contentDescription = "Estado Sensor",
                            tint = if (dashboardViewModel.sensorDetected.value) VerdeNormal else RojoAlerta,
                            modifier = Modifier.size(20.dp)
                        )
                        if (!dashboardViewModel.sensorDetected.value) {
                            Surface(
                                modifier = Modifier.size(8.dp).align(Alignment.TopEnd),
                                shape = CircleShape,
                                color = RojoAlerta
                            ) {}
                        }
                    }

                    FilledTonalIconButton(
                        onClick = onOpenProfile,
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = Color.White.copy(alpha = 0.1f),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
                    }
                }
            }

            // TARJETA BPM
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = CardBlanca)
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
                                text = dashboardViewModel.bpm.value, // BPM ahora viene de /ir
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF222222)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "BPM",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF666666)
                                )
                            )
                        }
                        Box(modifier = Modifier.height(24.dp).width(96.dp))
                    }

                    AssistChip(
                        onClick = {},
                        label = { Text(
                            if (dashboardViewModel.alert.value.isEmpty()) "NORMAL" else "ALERTA",
                            fontSize = 12.sp
                        ) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (dashboardViewModel.alert.value.isEmpty())
                                VerdeNormal.copy(alpha = 0.12f) else RojoAlerta.copy(alpha = 0.2f),
                            labelColor = if (dashboardViewModel.alert.value.isEmpty()) VerdeNormal else RojoAlerta
                        )
                    )
                }
            }

            // OXIGENACIÓN Y TEMPERATURA
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // SpO₂ dinámico
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBlanca)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Outlined.WaterDrop, contentDescription = "Oxigenación", tint = Color(0xFF1A2B4A), modifier = Modifier.size(20.dp))
                            Text("SpO₂", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF666666)))
                        }
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = dashboardViewModel.oxigen.value,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF222222)
                            )
                            Text("%", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF666666)), modifier = Modifier.padding(start = 2.dp))
                        }
                        AssistChip(
                            onClick = {},
                            label = { Text("ÓPTIMO", fontSize = 10.sp) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = VerdeNormal.copy(alpha = 0.12f),
                                labelColor = VerdeNormal
                            ),
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }

                // Temperatura dinámica
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBlanca)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Outlined.DeviceThermostat, contentDescription = "Temperatura", tint = Color(0xFF1A2B4A), modifier = Modifier.size(20.dp))
                            Text("Temp", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF666666)))
                        }
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = dashboardViewModel.temp.value,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF222222)
                            )
                            Text("°C", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF666666)), modifier = Modifier.padding(start = 2.dp))
                        }
                        AssistChip(
                            onClick = {},
                            label = { Text("NORMAL", fontSize = 10.sp) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = VerdeNormal.copy(alpha = 0.12f),
                                labelColor = VerdeNormal
                            ),
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            // ----------------------
            // DEBUG PANEL
            // ----------------------
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("DEBUG PANEL", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("BPM: ${dashboardViewModel.bpm.value}", color = Color.White)
                    Text("O₂: ${dashboardViewModel.oxigen.value}", color = Color.White)
                    Text("Temp: ${dashboardViewModel.temp.value} °C", color = Color.White)
                    Text("WiFi: ${dashboardViewModel.wifiConnected.value}", color = Color.White)
                    Text("Sensor: ${dashboardViewModel.sensorDetected.value}", color = Color.White)
                }
            }

            // ACCIONES RÁPIDAS
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachRow = 3,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                QuickAction(icon = Icons.Outlined.FavoriteBorder, text = "Monitoreo", onClick = onOpenMonitor)
                QuickAction(icon = Icons.Outlined.BarChart, text = "Salud Mental", onClick = { })
                QuickAction(icon = Icons.Outlined.Alarm, text = "Alertas", onClick = onOpenAlerts)
                QuickAction(icon = Icons.Outlined.History, text = "Historial", onClick = onOpenHistory)
                QuickAction(icon = Icons.Outlined.Person, text = "Perfil", onClick = onOpenProfile)
            }
        }
    }
}

// ---------------------
// QuickAction composable
// ---------------------
@Composable
private fun QuickAction(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.width(120.dp).height(90.dp),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(width = 1.dp, color = Color.White.copy(alpha = 0.20f)),
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
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}