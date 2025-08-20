// app/src/main/java/com/meshwave/p2ptestbed/ui/MainViewModel.kt
// VERSÃO 1.1.0 - Conecta o ViewModel à API para buscar Tópicos de Missão reais.

package com.meshwave.p2ptestbed.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meshwave.p2ptestbed.data.Interaction
import com.meshwave.p2ptestbed.data.MissionTopic
import com.meshwave.p2ptestbed.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

// O Estado da UI permanece o mesmo
data class SofiaUiState(
    val missionTopics: List<MissionTopic> = emptyList(),
    val currentInteractions: List<Interaction> = emptyList(),
    val selectedTopic: MissionTopic? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SofiaUiState())
    val uiState: StateFlow<SofiaUiState> = _uiState.asStateFlow()

    private val repository = TaskRepository()

    // --- [MUDANÇA 1 de 3] ---
    // Um client_id fixo para o MVP. No futuro, isso virá de um login.
    private val clientId = "ANDROID_USER_001"

    init {
        // Carrega os tópicos de missão reais da API ao iniciar.
        loadMissionTopics()
    }

    // --- [MUDANÇA 2 de 3] ---
    // A função agora busca dados reais da API.
    private fun loadMissionTopics() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val response = repository.getMissionTopics(clientId)
                if (response.isSuccessful) {
                    val topicsDto = response.body() ?: emptyList()
                    // Mapeia o DTO da camada de dados para o modelo da camada de UI
                    val topicsUi = topicsDto.map { dto ->
                        MissionTopic(
                            id = dto.taskCode, // Usamos o task_code como ID único na UI
                            title = dto.taskName,
                            // Valores de exemplo para campos que ainda não vêm da API
                            lastInteraction = "Clique para ver as interações...",
                            timestamp = ""
                        )
                    }
                    _uiState.update { it.copy(missionTopics = topicsUi, isLoading = false) }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Erro desconhecido na API"
                    handleError("Falha ao buscar tópicos: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                handleError("Erro de rede ao buscar tópicos: ${e.message}")
            }
        }
    }

    // --- [MUDANÇA 3 de 3] ---
    // Uma função para lidar com erros e exibi-los na UI.
    private fun handleError(message: String) {
        Log.e("MainViewModel", message)
        _uiState.update { it.copy(isLoading = false, errorMessage = message) }
    }

    // O restante das funções permanece o mesmo por enquanto, usando dados de exemplo
    // para a tela de chat.

    fun selectTopic(topic: MissionTopic) {
        _uiState.update { it.copy(selectedTopic = topic, isLoading = true) }
        // Lógica futura: Buscar as interações deste tópico na API
        // Por agora, usamos dados de exemplo:
        val exampleInteractions = listOf(
            Interaction("MSG-01", "Quais são os principais concorrentes do nosso novo produto?", "user"),
            Interaction("MSG-02", "Analisando... Os principais concorrentes são Empresa A, Empresa B e Startup C.", "sofia"),
            Interaction("MSG-03", "Qual a faixa de preço deles?", "user"),
            Interaction("MSG-04", "A faixa de preço varia de $50 a $85, dependendo do modelo.", "sofia")
        )
        _uiState.update { it.copy(currentInteractions = exampleInteractions, isLoading = false) }
    }

    fun deselectTopic() {
        _uiState.update { it.copy(selectedTopic = null, currentInteractions = emptyList()) }
    }



    fun sendInteraction(prompt: String, topicId: String) {
        val newInteraction = Interaction(UUID.randomUUID().toString(), prompt, "user")
        _uiState.update {
            it.copy(currentInteractions = it.currentInteractions + newInteraction)
        }

        // Lógica futura: Enviar a interação para a API e receber a resposta
        viewModelScope.launch {
            // Simula uma chamada de API
            kotlinx.coroutines.delay(1500)
            val sofiaResponse = Interaction(UUID.randomUUID().toString(), "Entendido. Processando sua solicitação sobre: '$prompt'", "sofia")
            _uiState.update {
                it.copy(currentInteractions = it.currentInteractions + sofiaResponse)
            }
        }
    }
}

