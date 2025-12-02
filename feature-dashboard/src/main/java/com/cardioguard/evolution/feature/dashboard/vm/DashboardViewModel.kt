package com.cardioguard.evolution.feature.dashboard.vm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class DashboardViewModel : ViewModel() {

    // Estados de Compose
    val bpm = mutableStateOf("0")            // ahora /ir es BPM
    val oxigen = mutableStateOf("0")
    val temp = mutableStateOf("0.0")
    val sensorDetected = mutableStateOf(false) // finger
    val wifiConnected = mutableStateOf(false)
    val alert = mutableStateOf("")
    val alertCount = mutableStateOf(0L)

    // Historial de BPM para mini-gráfica
    val bpmHistory = mutableStateOf(listOf<Int>())

    // Configura tu broker MQTT
    private val brokerUrl = "tcp://161.132.4.162:1883" // broker real
    private val clientId = "android_client"

    private lateinit var mqttClient: MqttClient

    init {
        connectMqtt()
    }

    private fun connectMqtt() {
        try {
            mqttClient = MqttClient(brokerUrl, clientId, MemoryPersistence())
            val options = MqttConnectOptions().apply {
                isCleanSession = true
            }

            mqttClient.connect(options)
            Log.d("DashboardVM", "MQTT conectado a $brokerUrl")

            // Suscribirse a todos los topics de CardioGuard
            mqttClient.subscribe("cardioguard/#") { topic, message ->
                parseTopic(topic, message.toString())
            }

        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    // Parsear todos los topics
    private fun parseTopic(topic: String, value: String) {
        when {
            topic.endsWith("/ir") -> {
                // /ir ahora representa BPM
                val bpmVal = value.toIntOrNull()?.coerceAtMost(999) ?: 0
                bpm.value = bpmVal.toString()

                // Actualizar historial de BPM (últimos 20 datos)
                val updatedHistory = (bpmHistory.value + bpmVal).takeLast(20)
                bpmHistory.value = updatedHistory

                // Alertas por BPM alto
                if (bpmVal > 75) { // ajusta el umbral si quieres
                    alert.value = "Pulsaciones altas!"
                    alertCount.value += 1
                } else {
                    alert.value = ""
                }
            }
            topic.endsWith("/oxigen") || topic.endsWith("/spo2") -> oxigen.value = value
            topic.endsWith("/temp") || topic.endsWith("/temperature") -> temp.value = value
            topic.endsWith("/finger") -> sensorDetected.value = value == "1" || value.equals("SI", true)
            topic.endsWith("/wifi") -> wifiConnected.value = value.equals("ON", true)
        }

        // Log para depuración
        Log.d(
            "DashboardVM",
            "Topic=$topic, Value=$value, BPM=${bpm.value}, O₂=${oxigen.value}, Temp=${temp.value}, Finger=${sensorDetected.value}, WiFi=${wifiConnected.value}, Alert=${alert.value}, Count=${alertCount.value}"
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (::mqttClient.isInitialized && mqttClient.isConnected) mqttClient.disconnect()
    }
}
