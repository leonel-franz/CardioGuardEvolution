package com.cardioguard.evolution.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onOpenPrivacySettings: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil Médico", color = Color.White) },
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
    ) { padding ->

        // Fondo gradiente
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // DATOS PERSONALES
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Datos Personales",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFF333333),
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Nombre",
                                        fontSize = 11.sp,
                                        color = Color(0xFF888888)
                                    )
                                    Text(
                                        text = "Roberto Martínez",
                                        fontSize = 14.sp,
                                        color = Color(0xFF333333)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Edad",
                                        fontSize = 11.sp,
                                        color = Color(0xFF888888)
                                    )
                                    Text(
                                        text = "58 años",
                                        fontSize = 14.sp,
                                        color = Color(0xFF333333)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Peso",
                                        fontSize = 11.sp,
                                        color = Color(0xFF888888)
                                    )
                                    Text(
                                        text = "78 kg",
                                        fontSize = 14.sp,
                                        color = Color(0xFF333333)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Altura",
                                        fontSize = 11.sp,
                                        color = Color(0xFF888888)
                                    )
                                    Text(
                                        text = "175 cm",
                                        fontSize = 14.sp,
                                        color = Color(0xFF333333)
                                    )
                                }
                            }
                        }
                    }
                }

                // HISTORIAL MÉDICO
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Historial Médico",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFF333333),
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Column {
                                Text(
                                    text = "Condiciones",
                                    fontSize = 11.sp,
                                    color = Color(0xFF888888)
                                )
                                Spacer(Modifier.height(4.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    ChipTag(
                                        text = "Hipertensión",
                                        bg = Color(0xFFE74C3C).copy(alpha = 0.10f),
                                        fg = Color(0xFFE74C3C)
                                    )
                                    ChipTag(
                                        text = "Prediabetes",
                                        bg = Color(0xFFF2F2F2),
                                        fg = Color(0xFF555555)
                                    )
                                }
                            }

                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "Medicamentos",
                                    fontSize = 11.sp,
                                    color = Color(0xFF888888)
                                )

                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFF7F7F7)
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Column(Modifier.padding(10.dp)) {
                                        Text(
                                            text = "Enalapril 10mg",
                                            fontSize = 14.sp,
                                            color = Color(0xFF333333)
                                        )
                                        Text(
                                            text = "1 vez al día - Mañana",
                                            fontSize = 11.sp,
                                            color = Color(0xFF888888)
                                        )
                                    }
                                }

                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFF7F7F7)
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Column(Modifier.padding(10.dp)) {
                                        Text(
                                            text = "Aspirina 100mg",
                                            fontSize = 14.sp,
                                            color = Color(0xFF333333)
                                        )
                                        Text(
                                            text = "1 vez al día - Noche",
                                            fontSize = 11.sp,
                                            color = Color(0xFF888888)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // ALERGIAS
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Alergias",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFF333333),
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            AllergyItem("Penicilina")
                            AllergyItem("Mariscos")
                        }
                    }
                }

                // ANTECEDENTES FAMILIARES
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.95f)
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Antecedentes Familiares",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFF333333),
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            BulletItem(
                                color = Color(0xFFE74C3C),
                                text = "Padre: Enfermedad cardíaca (infarto a los 65)"
                            )
                            BulletItem(
                                color = Color(0xFFF39C12),
                                text = "Madre: Diabetes tipo 2"
                            )
                        }
                    }
                }

                // ACCIONES
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { /* TODO: generar reporte */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Description,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Generar Reporte Médico Completo")
                    }

                    Button(
                        onClick = onOpenPrivacySettings,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White.copy(alpha = 0.08f),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Configuración de Privacidad")
                    }
                }
            }
        }
    }
}

@Composable
private fun ChipTag(text: String, bg: Color, fg: Color) {
    Box(
        modifier = Modifier
            .background(bg, shape = RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text = text, fontSize = 12.sp, color = fg)
    }
}

@Composable
private fun AllergyItem(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF39C12).copy(alpha = 0.10f),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFF39C12).copy(alpha = 0.30f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = "⚠️ $label",
            fontSize = 14.sp,
            color = Color(0xFF333333)
        )
    }
}

@Composable
private fun BulletItem(color: Color, text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(color = color, shape = RoundedCornerShape(50))
        )
        Text(text = text, fontSize = 13.sp, color = Color(0xFF555555))
    }
}
