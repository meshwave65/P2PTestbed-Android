// app/src/main/java/com/meshwave/p2ptestbed/data/TaskRepository.kt
// VERSÃO 1.1.0 - Adiciona a função para buscar os Tópicos de Missão.

package com.meshwave.p2ptestbed.data

import retrofit2.Response

class TaskRepository {
    private val apiService = ApiClient.apiService

    /**
     * Chama a API para criar uma nova tarefa (ou uma nova interação).
     * @param description O conteúdo da tarefa/interação.
     * @return A resposta da API.
     */
    suspend fun createTask(description: String): Response<TaskCreateResponse> {
        val request = TaskCreateRequest(description = description)
        return apiService.createTask(request)
    }

    // --- [NOVA FUNÇÃO ADICIONADA AQUI] ---

    /**
     * Chama a API para buscar a lista de Tópicos de Missão de um usuário.
     * @param clientId O ID do cliente a ser consultado.
     * @return A resposta da API contendo a lista de tópicos.
     */
    suspend fun getMissionTopics(clientId: String): Response<List<MissionTopicDto>> {
        return apiService.getMissionTopics(clientId)
    }
}

