package com.cardioguard.evolution.feature.dashboard.vm

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.io.File
import java.io.FileOutputStream

class DashboardViewModel : ViewModel() {

    // Estados de Compose
    val bpm = mutableStateOf("0")
    val oxigen = mutableStateOf("0")
    val temp = mutableStateOf("0.0")
    val sensorDetected = mutableStateOf(false)
    val wifiConnected = mutableStateOf(false)
    val alert = mutableStateOf("")
    val alertCount = mutableStateOf(0L)

    val bpmHistory = mutableStateOf(listOf<Int>())

    private val brokerUrl = "tcp://161.132.4.162:1883"
    private val clientId = "android_client"
    private lateinit var mqttClient: MqttClient

    init {
        connectMqtt()
    }

    private fun connectMqtt() {
        try {
            mqttClient = MqttClient(brokerUrl, clientId, MemoryPersistence())
            val options = MqttConnectOptions().apply { isCleanSession = true }
            mqttClient.connect(options)
            Log.d("DashboardVM", "MQTT conectado a $brokerUrl")

            // Suscribirse a todos los topics
            mqttClient.subscribe("cardioguard/#") { topic, message ->
                val value = message.toString()
                // Garantizar update en hilo principal para Compose
                viewModelScope.launch(Dispatchers.Main) {
                    parseTopic(topic, value)
                }
            }

        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    private fun parseTopic(topic: String, value: String) {
        when {
            topic.endsWith("/ir") || topic.endsWith("/bpm") -> {
                val bpmVal = value.toIntOrNull()?.coerceAtMost(999) ?: 0
                bpm.value = bpmVal.toString()
                bpmHistory.value = (bpmHistory.value + bpmVal).takeLast(20)

                if (bpmVal > 75) {
                    alert.value = "Pulsaciones altas!"
                    alertCount.value += 1
                } else {
                    alert.value = ""
                }
            }
            topic.endsWith("/oxigen") || topic.endsWith("/spo2") -> oxigen.value = value.toString()
            topic.endsWith("/temp") || topic.endsWith("/temperature") -> temp.value = value.toString()
            topic.endsWith("/finger") -> sensorDetected.value = value == "1" || value.equals("SI", true)
            topic.endsWith("/wifi") -> wifiConnected.value = value.equals("ON", true)
        }

        Log.d(
            "DashboardVM",
            "Topic=$topic, Value=$value, BPM=${bpm.value}, O₂=${oxigen.value}, Temp=${temp.value}, Finger=${sensorDetected.value}, WiFi=${wifiConnected.value}, Alert=${alert.value}, Count=${alertCount.value}"
        )
    }

    /** Compartir reporte como texto */
    fun shareReport(context: Context) {
        val report = """
            CardioGuard Report:
            BPM: ${bpm.value}
            O₂: ${oxigen.value}
            Temp: ${temp.value}
            Alert: ${alert.value}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, report)
        }
        context.startActivity(Intent.createChooser(intent, "Compartir reporte"))
    }

    /** Exportar reporte como PDF */
    fun exportPdf(context: Context) {
        try {
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(300, 400, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas: Canvas = page.canvas
            val paint = Paint().apply { textSize = 12f }

            val reportLines = listOf(
                "CardioGuard Report",
                "=================",
                "BPM: ${bpm.value}",
                "O₂: ${oxigen.value}",
                "Temp: ${temp.value}",
                "Alert: ${alert.value}"
            )

            reportLines.forEachIndexed { index, line ->
                canvas.drawText(line, 10f, 25f + index * 20f, paint)
            }

            pdfDocument.finishPage(page)
            val file = File(context.cacheDir, "cardioguard_report.pdf")
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Compartir PDF"))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (::mqttClient.isInitialized && mqttClient.isConnected) mqttClient.disconnect()
    }
}
