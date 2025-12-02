package com.cardioguard.evolution.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cardioguard.evolution.design.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.Button

@Composable
fun TermsAndConditionsDialog(
    open: Boolean,
    onDismiss: () -> Unit,   // ⬅️ NUEVO
    onAccept: () -> Unit
) {
    var acceptedTerms by remember { mutableStateOf(false) }
    var acceptedPrivacy by remember { mutableStateOf(false) }

    if (open) {
        Dialog(onDismissRequest = onDismiss) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, RoundedCornerShape(18.dp))
            ) {

                // ------------ HEADER ------------
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F294C))
                        .padding(top = 28.dp, bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_shield),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(54.dp)
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "Bienvenido a CardioGuard",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Por favor, revisa y acepta nuestros términos para continuar",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                    )
                }

                // ------------ CONTENIDO SCROLL ------------
                Column(
                    modifier = Modifier
                        .heightIn(max = 430.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text("Términos y Condiciones", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)

                    Text(
                        "1. Uso de la Aplicación: CardioGuard Evolution es una herramienta de monitoreo de salud complementaria y no sustituye el consejo médico profesional.\n" +
                                "2. Responsabilidad: La información proporcionada por la app es orientativa. Ante cualquier emergencia médica, contacte inmediatamente a servicios de emergencia.\n" +
                                "3. Precisión de Datos: Si bien nos esforzamos por proporcionar información precisa, no garantizamos la exactitud absoluta de las mediciones\n" +
                                "4. Uso Adecuado: El usuario se compromete a usar la aplicación de manera responsable y ética.\n" +
                                "5. Actualizaciones: Nos reservamos el derecho de modificar estos términos con notificación previa.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Text("Política de Privacidad", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)

                    Text(
                        "Recopilación de Datos: Recopilamos datos de salud como frecuencia cardíaca, patrones de respiración y estado emocional para brindarte una experiencia personalizada.\n" +
                                "Uso de Información: Tus datos se utilizan exclusivamente para mejorar tu experiencia en la app y proporcionar recomendaciones personalizadas.\n" +
                                "Seguridad: Implementamos medidas de seguridad avanzadas para proteger tu información personal y médica mediante encriptación de extremo a extremo.\n" +
                                "Compartir Datos: Nunca compartiremos tus datos personales o de salud con terceros sin tu consentimiento explícito.\n" +
                                "Tus Derechos: Tienes derecho a acceder, modificar o eliminar tus datos en cualquier momento desde la configuración de la app.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    // ------------ ADVERTENCIA ------------
                    Column(
                        modifier = Modifier
                            .background(Color(0xFFFFF4D6), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            "⚠️ Advertencia Médica Importante:",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7A4A00),
                            fontSize = 14.sp
                        )

                        Text(
                            "Esta aplicación NO es un dispositivo médico certificado. Si experimentas dolor en el pecho, dificultad para respirar, o cualquier síntoma grave, llama inmediatamente al número de emergencias de tu país.",
                            fontSize = 12.sp,
                            color = Color(0xFF7A4A00)
                        )
                    }

                    // ------------ CHECKBOXES ------------
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = acceptedTerms,
                            onCheckedChange = { acceptedTerms = it }
                        )
                        Text("Acepto los Términos y Condiciones", color = Color.Black)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = acceptedPrivacy,
                            onCheckedChange = { acceptedPrivacy = it }
                        )
                        Text("Acepto la Política de Privacidad", color = Color.Black)
                    }
                }

                // ------------ BOTÓN ------------
                val enabled = acceptedTerms && acceptedPrivacy

                val animatedColor by animateColorAsState(
                    targetValue = if (enabled) Color(0xFF4CAF50) else Color(0xFFBFC5C8),
                    animationSpec = tween(300)
                )

                Button(
                    onClick = { if (enabled) onAccept() },
                    enabled = true, // ⬅️ se ve SIEMPRE pero solo acepta si está habilitado
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = animatedColor
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        "Aceptar",
                        color = if (enabled) Color.White else Color(0xFF5A5A5A)
                    )
                }
            }
        }
    }
}