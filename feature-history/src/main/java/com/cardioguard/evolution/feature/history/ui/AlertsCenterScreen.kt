package com.cardioguard.evolution.feature.history.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores locales para imitar el diseño de tus pantallas
private val AzulPrimario = Color(0xFF1A2B4A)
private val AzulOscuro = Color(0xFF0F1A2E)
private val CardBlanca = Color(0xFFF9FAFB)
private val RojoAlerta = Color(0xFFE74C3C)
private val AmarilloAlerta = Color(0xFFF39C12)
private val GrisInfo = Color(0xFF95A5A6)

/** Datos de ejemplo, igual que en el TSX */

private data class ActiveAlert(
    val id: Int,
    val time: String,
    val type: String,
    val bpm: Int,
    val action: String,
    val severity: Severity
)

private data class HistoryAlert(
    val id: Int,
    val date: String,
    val type: String,
    val severity: Severity
)

private enum class Severity { Critical, Warning, Info }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsCenterScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var alertsEnabled by remember { mutableStateOf(true) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var bpmThreshold by remember { mutableFloatStateOf(120f) }

    val activeAlerts = remember {
        listOf(
            ActiveAlert(
                id = 1,
                time = "09:20 AM",
                type = "Crisis Cardíaca",
                bpm = 142,
                action = "Notificado familia y médico",
                severity = Severity.Critical
            )
        )
    }

    val alertHistory = remember {
        listOf(
            HistoryAlert(1, "Hoy 09:20", "Crisis Cardíaca", Severity.Critical),
            HistoryAlert(2, "Hoy 08:00", "Pánico", Severity.Warning),
            HistoryAlert(3, "Ayer 16:30", "Estrés Alto", Severity.Info),
            HistoryAlert(4, "Hace 2 días", "BPM Elevado", Severity.Warning),
        )
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Centro de Alertas",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(AzulPrimario, AzulOscuro)
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Tarjeta "Sistema de Alertas"
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBlanca),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = null,
                                tint = AzulPrimario
                            )
                            Column {
                                Text(
                                    text = "Sistema de Alertas",
                                    color = Color(0xFF222222),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = if (alertsEnabled) "Activo" else "Desactivado",
                                    color = Color(0xFF777777),
                                    fontSize = 12.sp
                                )
                            }
                        }
                        Switch(
                            checked = alertsEnabled,
                            onCheckedChange = { alertsEnabled = it }
                        )
                    }
                }

                // Tabs: Activas / Historial / Configurar
                val tabs = listOf("Activas", "Historial", "Configurar")

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.White.copy(alpha = 0.7f),
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 13.sp,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }

                when (selectedTabIndex) {
                    0 -> ActiveAlertsTab(activeAlerts)
                    1 -> AlertHistoryTab(alertHistory)
                    2 -> AlertsConfigTab(
                        bpmThreshold = bpmThreshold,
                        onBpmThresholdChange = { bpmThreshold = it }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ActiveAlertsTab(
    alerts: List<ActiveAlert>,
    modifier: Modifier = Modifier
) {
    if (alerts.isEmpty()) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBlanca),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay alertas activas",
                    color = Color(0xFF777777),
                    fontSize = 14.sp
                )
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            alerts.forEach { alert ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBlanca),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            tint = RojoAlerta,
                            modifier = Modifier
                                .size(28.dp)
                                .align(Alignment.Top)
                        )

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = alert.type,
                                    color = Color(0xFF222222),
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = alert.time,
                                    color = Color(0xFF888888),
                                    fontSize = 12.sp
                                )
                            }

                            Text(
                                text = "${alert.bpm} BPM",
                                color = RojoAlerta,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = "Acción: ${alert.action}",
                                color = Color(0xFF666666),
                                fontSize = 13.sp
                            )

                            Button(
                                onClick = { /* TODO: navega a detalles */ },
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .align(Alignment.Start),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "Ver Detalles",
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertHistoryTab(
    alerts: List<HistoryAlert>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        alerts.forEach { alert ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBlanca),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = when (alert.severity) {
                                        Severity.Critical -> RojoAlerta
                                        Severity.Warning -> AmarilloAlerta
                                        Severity.Info -> GrisInfo
                                    },
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )

                        Column {
                            Text(
                                text = alert.type,
                                color = Color(0xFF222222),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = alert.date,
                                color = Color(0xFF888888),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Button(
                        onClick = { /* TODO: acciones de historial */ },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Ver",
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertsConfigTab(
    bpmThreshold: Float,
    onBpmThresholdChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ---- Umbrales ----
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBlanca),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Umbrales",
                    color = Color(0xFF222222),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "BPM Alerta",
                            color = Color(0xFF777777),
                            fontSize = 13.sp
                        )
                        Text(
                            text = "${bpmThreshold.toInt()} BPM",
                            color = RojoAlerta,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Slider(
                        value = bpmThreshold,
                        onValueChange = onBpmThresholdChange,
                        valueRange = 80f..160f
                    )
                }
            }
        }

        // ---- Contactos de Emergencia ----
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBlanca),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Contactos de Emergencia",
                    color = Color(0xFF222222),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dr. García (Cardiólogo)",
                        color = Color(0xFF777777),
                        fontSize = 13.sp
                    )
                    Switch(checked = true, onCheckedChange = { /* TODO */ })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "María (Familia)",
                        color = Color(0xFF777777),
                        fontSize = 13.sp
                    )
                    Switch(checked = true, onCheckedChange = { /* TODO */ })
                }
            }
        }

        // ---- Notificaciones ----
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBlanca),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Notificaciones",
                    color = Color(0xFF222222),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sonido",
                        color = Color(0xFF777777),
                        fontSize = 13.sp
                    )
                    Switch(checked = true, onCheckedChange = { /* TODO */ })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Vibración",
                        color = Color(0xFF777777),
                        fontSize = 13.sp
                    )
                    Switch(checked = true, onCheckedChange = { /* TODO */ })
                }
            }
        }
    }
}
