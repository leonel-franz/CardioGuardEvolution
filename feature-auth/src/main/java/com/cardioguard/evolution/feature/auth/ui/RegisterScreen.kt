package com.cardioguard.evolution.feature.auth.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cardioguard.evolution.feature.auth.navigation.Route
import com.cardioguard.evolution.feature.auth.vm.AuthViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,            // ← agregamos NavController
    viewModel: AuthViewModel = hiltViewModel(),
    onRegistered: (String) -> Unit
) {

    // -------------------- STATES --------------------
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var fechaNacimiento by remember { mutableStateOf("") }

    // === Mapa de géneros ===
    val generoMap = mapOf("Masculino" to "M", "Femenino" to "F", "Otro" to "Otro")
    val generoOptions = generoMap.keys.toList()
    var genero by remember { mutableStateOf("") }
    var showGeneroDropdown by remember { mutableStateOf(false) }

    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onRegistered("$nombres $apellidos")
    }

    // ----------- BACKGROUND GRADIENT PRO -----------
    val bg = Brush.verticalGradient(
        0f to Color(0xFF1A2B4A),
        1f to Color(0xFF0F1A2E)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {

            // ---------- VOLVER ----------
            Text(
                text = "← Volver",
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier
                    .clickable { navController.popBackStack() }  // usa navController para volver
                    .padding(4.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Crear Cuenta",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Únete a CardioGuard Evolution",
                color = Color.White.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(32.dp))

            // ================== ESTILO UNIFICADO ==================
            val fieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White.copy(alpha = 0.25f),
                unfocusedBorderColor = Color.White.copy(alpha = 0.20f),
                focusedContainerColor = Color.White.copy(alpha = 0.10f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.08f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            )

            fun Modifier.fieldShape() = this
                .fillMaxWidth()
                .height(58.dp)

            // ================== CAMPOS ==================
            OutlinedTextField(
                value = nombres,
                onValueChange = { nombres = it },
                placeholder = { Text("Nombres", color = Color.White.copy(alpha = 0.5f)) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors,
                modifier = Modifier.fieldShape()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                placeholder = { Text("Apellidos", color = Color.White.copy(alpha = 0.5f)) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors,
                modifier = Modifier.fieldShape()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Correo electrónico", color = Color.White.copy(alpha = 0.5f)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors,
                modifier = Modifier.fieldShape()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Contraseña", color = Color.White.copy(alpha = 0.5f)) },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Text(
                        if (showPassword) "Ocultar" else "Ver",
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.clickable { showPassword = !showPassword }
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors,
                modifier = Modifier.fieldShape()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirmar contraseña", color = Color.White.copy(alpha = 0.5f)) },
                singleLine = true,
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Text(
                        if (showConfirmPassword) "Ocultar" else "Ver",
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.clickable { showConfirmPassword = !showConfirmPassword }
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors,
                modifier = Modifier.fieldShape()
            )

            Spacer(Modifier.height(12.dp))

            // ================== FECHA ==================
            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                placeholder = { Text("Fecha de nacimiento", color = Color.White.copy(alpha = 0.5f)) },
                modifier = Modifier
                    .fieldShape()
                    .clickable {
                        DatePickerDialog(
                            context,
                            { _: DatePicker, y, m, d ->
                                fechaNacimiento = "$y-${m + 1}-$d"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                shape = RoundedCornerShape(16.dp),
                colors = fieldColors
            )

            Spacer(Modifier.height(12.dp))

            // ================== GÉNERO ==================
            ExposedDropdownMenuBox(
                expanded = showGeneroDropdown,
                onExpandedChange = { showGeneroDropdown = !showGeneroDropdown }
            ) {
                OutlinedTextField(
                    value = generoMap.entries.find { it.value == genero }?.key.orEmpty(),
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Género", color = Color.White.copy(alpha = 0.5f)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = showGeneroDropdown)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = fieldColors,
                    modifier = Modifier
                        .menuAnchor()
                        .fieldShape()
                )

                ExposedDropdownMenu(
                    expanded = showGeneroDropdown,
                    onDismissRequest = { showGeneroDropdown = false }
                ) {
                    generoOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                genero = generoMap[option]!!
                                showGeneroDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ================== BOTÓN REGISTRAR ==================
            Button(
                onClick = {
                    viewModel.register(
                        nombres, apellidos, email, password,
                        fechaNacimiento, genero
                    )
                },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2ECC71)
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Crear cuenta", color = Color.White)
                }
            }

            Spacer(Modifier.height(12.dp))

            // ================== BOTÓN LOGIN ==================
            TextButton(
                onClick = {
                    navController.navigate(Route.Login.path) {       // ← navega a LoginScreen
                        popUpTo(Route.Register.path) { inclusive = true }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = Color.White.copy(alpha = 0.7f))
            }
        }
    }
}
