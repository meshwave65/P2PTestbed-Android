// app/src/main/java/com/meshwave/p2ptestbed/ui/MainViewModel.kt
// VERSÃO 0.3.1 - Altera o prefixo da missão para TSK para maior clareza.

package com.meshwave.p2ptestbed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meshwave.p2ptestbed.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class SofiaState(
    val logMessages: List<String> = listOf("Bem-vindo ao SOFIA!"),
    val missionId: String? = null,
    val isMissionRunning: Boolean = false,
    val isDownloadEnabled: Boolean = false
)

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(SofiaState())
    val state: StateFlow<SofiaState> = _state.asStateFlow()

    private val repository = TaskRepository()

    fun addLog(message: String) {
        val timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val logEntry = "[$timestamp] $message"
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    logMessages = (currentState.logMessages + logEntry).takeLast(100)
                )
            }
        }
    }

    fun sendMission(prompt: String) {
        _state.update { it.copy(isMissionRunning = true) }
        addLog("Enviando missão para a API...")

        viewModelScope.launch {
            try {
                val response = repository.createTask(prompt)

                if (response.isSuccessful) {
                    val taskResponse = response.body()
                    if (taskResponse != null) {
                        // --- [A ÚNICA MUDANÇA ESTÁ AQUI] ---
                        val newTaskId = "TSK-${taskResponse.taskId}" // Alterado de TRK para TSK
                        _state.update {
                            it.copy(
                                isMissionRunning = false,
                                missionId = newTaskId
                            )
                        }
                        addLog("✅ Missão criada com sucesso! ID: $newTaskId")
                    } else {
                        handleApiError("Resposta da API bem-sucedida, mas corpo vazio.")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Erro desconhecido"
                    handleApiError("Falha na API: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                handleApiError("Erro de rede: ${e.message}")
            }
        }
    }

    private fun handleApiError(errorMessage: String) {
        _state.update { it.copy(isMissionRunning = false) }
        addLog("❌ ERRO: $errorMessage")
    }
}
