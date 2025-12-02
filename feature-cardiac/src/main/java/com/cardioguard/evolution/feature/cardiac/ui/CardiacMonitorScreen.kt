package com.cardioguard.evolution.feature.cardiac.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cardioguard.evolution.feature.cardiac.ui.theme.*
import com.cardioguard.evolution.feature.cardiac.vm.CardiacViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardiacMonitorScreen(
    bpmHistory: List<Int>,
    onBack: () -> Unit,
    onShare: () -> Unit,
    onExportPdf: () -> Unit
) {
    val context = LocalContext.current

    val bpmValue = bpmHistory.lastOrNull() ?: 0
    val bpmMin = bpmHistory.minOrNull() ?: 0
    val bpmMax = bpmHistory.maxOrNull() ?: 0
    val bpmAvg = if (bpmHistory.isNotEmpty()) bpmHistory.sum() / bpmHistory.size else 0

    val analysisText = when {
        bpmAvg < 60 -> "Riesgo de bradicardia. Consulta a tu médico."
        bpmAvg in 60..100 -> "Frecuencia estable dentro del rango normal."
        bpmAvg > 100 -> "Riesgo de taquicardia. Mantén control."
        else -> "Sin datos suficientes."
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monitor Cardíaco", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulPrimario,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = AzulOscuro
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- Gráfico interactivo ---
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBlanca)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Frecuencia Cardíaca", style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF444444)))

                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    ) {
                        if (bpmHistory.isEmpty()) {
                            drawLine(
                                Color.Gray,
                                start = Offset(0f, size.height / 2),
                                end = Offset(size.width, size.height / 2),
                                strokeWidth = 3f
                            )
                        } else {
                            val maxBpm = (bpmHistory.maxOrNull() ?: 100).toFloat()
                            val minBpm = (bpmHistory.minOrNull() ?: 40).toFloat()
                            val stepX = size.width / (bpmHistory.size - 1).coerceAtLeast(1)

                            bpmHistory.forEachIndexed { index, bpm ->
                                if (index > 0) {
                                    val prevBpm = bpmHistory[index - 1]
                                    val x1 = (index - 1) * stepX
                                    val x2 = index * stepX
                                    val y1 = size.height - ((prevBpm - minBpm) / (maxBpm - minBpm)) * size.height
                                    val y2 = size.height - ((bpm - minBpm) / (maxBpm - minBpm)) * size.height

                                    val lineColor = when {
                                        bpm in 60..100 -> VerdeNormal
                                        bpm < 60 -> NaranjaModerado
                                        bpm > 100 -> RojoAlerta
                                        else -> Color.Gray
                                    }

                                    drawLine(
                                        color = lineColor,
                                        start = Offset(x1, y1),
                                        end = Offset(x2, y2),
                                        strokeWidth = 4f,
                                        cap = StrokeCap.Round
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // --- Estadísticas ---
            Card(shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = CardBlanca)) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Estadísticas", style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF444444)))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Stat("Mínimo", bpmMin.toString(), VerdeNormal)
                        Stat("Promedio", bpmAvg.toString(), Color(0xFF222222))
                        Stat("Máximo", bpmMax.toString(), NaranjaModerado)
                    }
                }
            }

            // --- Análisis ---
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = VerdeNormal.copy(alpha = 0.10f)),
                border = BorderStroke(1.dp, VerdeNormal.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Análisis",
                        style = MaterialTheme.typography.titleMedium.copy(color = VerdeNormal, fontWeight = FontWeight.SemiBold)
                    )
                    Text(analysisText, style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF444444)))
                }
            }

            // --- Botones ---
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ElevatedButton(
                    onClick = { onShare() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.White.copy(alpha = 0.06f), contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f))
                ) {
                    Icon(Icons.Outlined.Share, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Compartir")
                }

                ElevatedButton(
                    onClick = { onExportPdf() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.White.copy(alpha = 0.06f), contentColor = Color.White),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f))
                ) {
                    Text("PDF")
                }
            }
        }
    }
}

@Composable
private fun Stat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF888888)))
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = color)
        Text("BPM", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFBBBBBB)))
    }
}
