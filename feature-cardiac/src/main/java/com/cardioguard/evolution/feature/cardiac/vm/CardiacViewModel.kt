package com.cardioguard.evolution.feature.cardiac.vm

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.io.File
import java.io.FileOutputStream

class CardiacViewModel : ViewModel() {

    private val _bpmHistory = MutableStateFlow<List<Int>>(emptyList())
    val bpmHistory: StateFlow<List<Int>> get() = _bpmHistory

    private var client: MqttClient? = null

    init {
        // Simula datos iniciales para que el gráfico no aparezca vacío
        viewModelScope.launch {
            _bpmHistory.value = List(10) { (60..100).random() }
        }

        connectMqtt()
    }

    private fun connectMqtt() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val brokerUrl = "tcp://161.132.4.162:1883"
                client = MqttClient(brokerUrl, MqttClient.generateClientId(), MemoryPersistence())
                val options = MqttConnectOptions().apply { isCleanSession = true }
                client?.connect(options)

                client?.subscribe("cardioguard/bpm") { _, msg ->
                    val bpm = msg.toString().toIntOrNull()
                    bpm?.let {
                        viewModelScope.launch {
                            val updated = (_bpmHistory.value + it).takeLast(50)
                            _bpmHistory.value = updated
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // aquí puedes reintentar la conexión si quieres
            }
        }
    }

    fun shareReport(context: Context) {
        val min = _bpmHistory.value.minOrNull() ?: 0
        val max = _bpmHistory.value.maxOrNull() ?: 0
        val avg = if (_bpmHistory.value.isNotEmpty()) _bpmHistory.value.sum() / _bpmHistory.value.size else 0
        val analysis = when {
            avg < 60 -> "Riesgo de bradicardia. Consulta a tu médico."
            avg in 60..100 -> "Frecuencia estable dentro del rango normal."
            avg > 100 -> "Riesgo de taquicardia. Mantén control."
            else -> "Sin datos suficientes."
        }

        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Reporte Monitor Cardíaco")
            putExtra(Intent.EXTRA_TEXT, """
                Monitor Cardíaco
                Mínimo: $min BPM
                Promedio: $avg BPM
                Máximo: $max BPM
                $analysis
            """.trimIndent())
        }
        context.startActivity(Intent.createChooser(sendIntent, "Compartir reporte"))
    }

    fun exportPdf(context: Context) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(400, 600, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = Paint().apply { textSize = 14f }

        canvas.drawText("Reporte Monitor Cardíaco", 10f, 20f, paint)
        _bpmHistory.value.forEachIndexed { index, bpm ->
            canvas.drawText("BPM[$index]: $bpm", 10f, 40f + index * 20f, paint)
        }

        val avg = if (_bpmHistory.value.isNotEmpty()) _bpmHistory.value.sum() / _bpmHistory.value.size else 0
        val analysis = when {
            avg < 60 -> "Riesgo de bradicardia. Consulta a tu médico."
            avg in 60..100 -> "Frecuencia estable dentro del rango normal."
            avg > 100 -> "Riesgo de taquicardia. Mantén control."
            else -> "Sin datos suficientes."
        }

        canvas.drawText("Análisis: $analysis", 10f, 40f + _bpmHistory.value.size * 20f, paint)

        pdfDocument.finishPage(page)
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "MonitorCardiaco.pdf")
        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()
    }
}
