package com.cardioguard.evolution.feature.cardiac.ui

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cardioguard.evolution.feature.cardiac.ui.theme.*
// AzulPrimario, AzulOscuro, VerdeNormal, NaranjaModerado, CardBlanca

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardiacMonitorScreen(
    onBack: () -> Unit,
    onShare: () -> Unit = {},
    onExportPdf: () -> Unit = {}
) {
    var selectedRange by remember { mutableStateOf("1d") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Monitor Cardíaco",
                        color = Color.White
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

            // --- FRECUENCIA CARDÍACA (tarjeta con gráfico) ---
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBlanca
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Frecuencia Cardíaca",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color(0xFF444444)
                        )
                    )

                    // Placeholder del gráfico (luego se puede implementar con Vico Charts o Canvas)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF7F7F7)
                    ) {
                        // Aquí luego va el gráfico real
                    }

                    // Selector de rango de tiempo
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        listOf("1h", "3h", "1d", "1s").forEach { range ->
                            TimeRangeChip(
                                label = range,
                                selected = selectedRange == range,
                                onClick = { selectedRange = range }
                            )
                        }
                    }
                }
            }

            // --- ESTADÍSTICAS ---
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBlanca
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Estadísticas",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color(0xFF444444)
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Stat(label = "Mínimo", value = "65", color = VerdeNormal)
                        Stat(label = "Promedio", value = "72", color = Color(0xFF222222))
                        Stat(label = "Máximo", value = "84", color = NaranjaModerado)
                    }
                }
            }

            // --- ANÁLISIS ---
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = VerdeNormal.copy(alpha = 0.10f)
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = VerdeNormal.copy(alpha = 0.3f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Análisis",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = VerdeNormal,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "Frecuencia estable, sin riesgo. Valores dentro del rango normal para tu edad y condición.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF444444)
                        )
                    )
                }
            }

            // --- BOTONES COMPARTIR / PDF ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Compartir
                ElevatedButton(
                    onClick = {
                        // Callback externo por si quieres enganchar analíticas, etc.
                        onShare()

                        // Texto a compartir (puedes cambiarlo o armarlo dinámicamente)
                        val shareText = """
                            Monitor Cardíaco

                            Frecuencia cardíaca:
                            - Mínimo: 65 BPM
                            - Promedio: 72 BPM
                            - Máximo: 84 BPM

                            Frecuencia estable, sin riesgo. Valores dentro del rango normal para tu edad y condición.
                        """.trimIndent()

                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Reporte de Monitor Cardíaco")
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        val chooser = Intent.createChooser(sendIntent, "Compartir reporte")
                        context.startActivity(chooser)
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.White.copy(alpha = 0.06f),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.25f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Compartir")
                }

                // PDF (sigue llamando al callback, que ya implementarás)
                ElevatedButton(
                    onClick = onExportPdf,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.White.copy(alpha = 0.06f),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.25f)
                    )
                ) {
                    Text("PDF")
                }
            }
        }
    }
}

@Composable
private fun TimeRangeChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) AzulPrimario else Color(0xFFF0F0F0)
    val fg = if (selected) Color.White else Color(0xFF555555)

    AssistChip(
        onClick = onClick,
        label = {
            Text(
                text = label,
                fontSize = 12.sp
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = bg,
            labelColor = fg
        ),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
private fun Stat(
    label: String,
    value: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF888888)
            )
        )
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = "BPM",
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color(0xFFBBBBBB)
            )
        )
    }
}
