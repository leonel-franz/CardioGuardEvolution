package com.cardioguard.evolution.feature.auth.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cardioguard.evolution.feature.auth.vm.AuthViewModel
import java.util.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegistered: (String) -> Unit,
    onBack: () -> Unit
) {

    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var fecha_nacimiento by remember { mutableStateOf("") }

    // --- MAPA PARA BD ---
    val generoMap = mapOf(
        "Masculino" to "M",
        "Femenino" to "F",
        "Otro" to "Otro"
    )

    val generoOptions = generoMap.keys.toList()

    var genero by remember { mutableStateOf("") } // ← Aquí almacenaré "M", "F" u "Otro"
    var showGeneroDropdown by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onRegistered("$nombres $apellidos")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = nombres,
            onValueChange = { nombres = it },
            label = { Text("Nombres") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(Modifier.height(8.dp))

        // ---------------- CONTRASEÑA ----------------

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Text(
                    if (showPassword) "Ocultar" else "Mostrar",
                    modifier = Modifier
                        .clickable { showPassword = !showPassword }
                        .padding(4.dp)
                )
            }
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Text(
                    if (showConfirmPassword) "Ocultar" else "Mostrar",
                    modifier = Modifier
                        .clickable { showConfirmPassword = !showConfirmPassword }
                        .padding(4.dp)
                )
            }
        )

        Spacer(Modifier.height(8.dp))

        // ---------------- FECHA DE NACIMIENTO ----------------

        OutlinedTextField(
            value = fecha_nacimiento,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            label = { Text("Fecha de nacimiento") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    DatePickerDialog(
                        context,
                        { _: DatePicker, year: Int, month: Int, day: Int ->
                            fecha_nacimiento = "$year-${month + 1}-$day"
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
        )

        Spacer(Modifier.height(8.dp))

        // ---------------- GENERO MODIFICADO (M / F / Otro ENVIADO A BD) ----------------

        ExposedDropdownMenuBox(
            expanded = showGeneroDropdown,
            onExpandedChange = { showGeneroDropdown = !showGeneroDropdown }
        ) {
            OutlinedTextField(
                value = generoMap.entries.find { it.value == genero }?.key ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Género") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = showGeneroDropdown)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = showGeneroDropdown,
                onDismissRequest = { showGeneroDropdown = false }
            ) {
                generoOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            genero = generoMap[option]!!  // ← ESTO ENVÍA M, F, Otro
                            showGeneroDropdown = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // ---------------- BOTÓN REGISTRAR ----------------

        Button(
            onClick = {
                viewModel.register(
                    nombres,
                    apellidos,
                    email,
                    password,
                    fecha_nacimiento,
                    genero  // ← AQUÍ YA LLEGA M, F u Otro
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading && password == confirmPassword
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Text("Registrar")
            }
        }

        if (password != confirmPassword) {
            Text("Las contraseñas no coinciden", color = Color.Red)
        }

        Spacer(Modifier.height(8.dp))

        TextButton(onClick = onBack) {
            Text("Volver")
        }

        state.error?.let {
            Text(it, color = Color.Red)
        }
    }
}
