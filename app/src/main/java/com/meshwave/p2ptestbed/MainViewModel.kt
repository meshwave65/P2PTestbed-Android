// app/src/main/java/com/meshwave/p2ptestbed/ui/MainViewModel.kt
// VERSÃO 0.2.0 - Adaptado para o estado da UI do SOFIA.

package com.meshwave.p2ptestbed.ui // Pacote correto, conforme sua estrutura

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// Novo estado para refletir a UI do SOFIA
data class SofiaState(
    val logMessages: List<String> = listOf("Bem-vindo ao SOFIA!"),
    val missionId: String? = null,
    val isMissionRunning: Boolean = false,
    val isDownloadEnabled: Boolean = false
)

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(SofiaState())
    val state: StateFlow<SofiaState> = _state.asStateFlow()

    // A função de log continua útil para feedback na tela
    fun addLog(message: String) {
        val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val logEntry = "[$timestamp] $message"
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    logMessages = currentState.logMessages + logEntry
                )
            }
        }
    }

    fun sendMission(prompt: String) {
        addLog("Enviando missão: $prompt")
        // TODO: Implementar a chamada de API real com Retrofit
        _state.update { it.copy(isMissionRunning = true, missionId = "TRK-FAKE-001") }
    }
}
