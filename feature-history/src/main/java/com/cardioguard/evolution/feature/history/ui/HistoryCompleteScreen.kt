package com.cardioguard.evolution.feature.history.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private enum class HistoryFilter {
    TODAY, D7, D30, D90
}

private data class HistoryEvent(
    val id: Int,
    val date: String,
    val time: String,
    val heart: Int,
    val heartStatus: String,
    val mental: String,
    val action: String,
    val severity: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryCompleteScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onExportCsv: () -> Unit = {},
    onShare: () -> Unit = {},
    onPrint: () -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf(HistoryFilter.D30) }
    var searchTerm by remember { mutableStateOf("") }

    val events = remember {
        listOf(
            HistoryEvent(
                id = 1,
                date = "Hoy",
                time = "09:20 AM",
                heart = 142,
                heartStatus = "Crisis",
                mental = "Pánico 85%",
                action = "Contactado médico",
                severity = "critical"
            ),
            HistoryEvent(
                id = 2,
                date = "Hoy",
                time = "08:00 AM",
                heart = 78,
                heartStatus = "Normal",
                mental = "Estrés 40%",
                action = "Monitoreo normal",
                severity = "normal"
            ),
            HistoryEvent(
                id = 3,
                date = "Ayer",
                time = "04:30 PM",
                heart = 135,
                heartStatus = "Elevado",
                mental = "Pánico 80%",
                action = "Técnica respiración",
                severity = "warning"
            ),
            HistoryEvent(
                id = 4,
                date = "Hace 2 días",
                time = "11:15 AM",
                heart = 72,
                heartStatus = "Normal",
                mental = "Calma 25%",
                action = "Sin acción requerida",
                severity = "good"
            ),
            HistoryEvent(
                id = 5,
                date = "Hace 3 días",
                time = "02:45 PM",
                heart = 95,
                heartStatus = "Moderado",
                mental = "Estrés 55%",
                action = "Alertada familia",
                severity = "warning"
            )
        )
    }

    val filteredEvents = events.filter { event ->
        if (searchTerm.isBlank()) return@filter true
        val query = searchTerm.lowercase()
        listOf(
            event.date,
            event.time,
            event.heartStatus,
            event.mental,
            event.action
        ).any { it.lowercase().contains(query) }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Histórico Completo", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A2B4A),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A2B4A),
                            Color(0xFF0F1A2E)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Filtros
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HistoryFilterChip(
                        label = "Hoy",
                        selected = selectedFilter == HistoryFilter.TODAY,
                        onClick = { selectedFilter = HistoryFilter.TODAY }
                    )
                    HistoryFilterChip(
                        label = "7 días",
                        selected = selectedFilter == HistoryFilter.D7,
                        onClick = { selectedFilter = HistoryFilter.D7 }
                    )
                    HistoryFilterChip(
                        label = "30 días",
                        selected = selectedFilter == HistoryFilter.D30,
                        onClick = { selectedFilter = HistoryFilter.D30 }
                    )
                    HistoryFilterChip(
                        label = "90 días",
                        selected = selectedFilter == HistoryFilter.D90,
                        onClick = { selectedFilter = HistoryFilter.D90 }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Buscador
                TextField(
                    value = searchTerm,
                    onValueChange = { searchTerm = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "Buscar eventos...",
                            color = Color.White.copy(alpha = 0.4f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.4f)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.10f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.10f),
                        disabledContainerColor = Color.White.copy(alpha = 0.10f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Lista de eventos
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredEvents, key = { it.id }) { event ->
                        HistoryEventCard(event = event)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Botones de acción inferiores
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ActionButton(
                        text = "CSV",
                        icon = Icons.Default.Download,
                        onClick = onExportCsv,
                        modifier = Modifier.weight(1f)
                    )
                    ActionButton(
                        text = "Compartir",
                        icon = Icons.Default.Share,
                        onClick = onShare,
                        modifier = Modifier.weight(1f)
                    )
                    ActionButton(
                        text = "Imprimir",
                        icon = Icons.Default.Print,
                        onClick = onPrint,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    if (selected) {
        Button(
            onClick = onClick,
            modifier = Modifier.height(32.dp),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF1A2B4A)
            )
        ) {
            Text(text = label, fontSize = 13.sp)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.height(32.dp),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White.copy(alpha = 0.10f),
                contentColor = Color.White
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.25f),
                        Color.White.copy(alpha = 0.15f)
                    )
                )
            )
        ) {
            Text(text = label, fontSize = 13.sp)
        }
    }
}

@Composable
private fun HistoryEventCard(
    event: HistoryEvent
) {
    val severityColor = when (event.severity) {
        "critical" -> Color(0xFFE74C3C)
        "warning" -> Color(0xFFF39C12)
        "good" -> Color(0xFF2ECC71)
        "normal" -> Color(0xFF95A5A6)
        else -> Color(0xFF95A5A6)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            // Barra de severidad
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(severityColor, shape = RoundedCornerShape(999.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = event.date,
                            color = Color(0xFF333333),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = event.time,
                            color = Color(0xFF999999),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "♥️ Cardíaco",
                            fontSize = 11.sp,
                            color = Color(0xFF777777)
                        )
                        Text(
                            text = "${event.heart} BPM",
                            fontSize = 14.sp,
                            color = severityColor,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = event.heartStatus,
                            fontSize = 11.sp,
                            color = Color(0xFF555555)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "🧠 Mental",
                            fontSize = 11.sp,
                            color = Color(0xFF777777)
                        )
                        Text(
                            text = event.mental,
                            fontSize = 14.sp,
                            color = Color(0xFF333333),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Acción: ${event.action}",
                        fontSize = 11.sp,
                        color = Color(0xFF555555)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = { /* TODO: abrir detalles */ },
                    modifier = Modifier.height(30.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF1A2B4A)
                    ),
                    contentPadding = PaddingValues(horizontal = 0.dp)
                ) {
                    Text(
                        text = "Ver detalles completos",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.10f),
            contentColor = Color.White
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.25f),
                    Color.White.copy(alpha = 0.15f)
                )
            )
        ),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(18.dp)
            )
            Text(text = text, fontSize = 11.sp)
        }
    }
}
