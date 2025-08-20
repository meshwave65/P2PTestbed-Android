// app/src/main/java/com/meshwave/p2ptestbed/data/TaskRepository.kt
// VERSÃO 1.0.1 - Estrutura de pacote corrigida.

package com.meshwave.p2ptestbed.data

import retrofit2.Response

class TaskRepository {
    private val apiService = ApiClient.apiService

    suspend fun createTask(description: String): Response<TaskCreateResponse> {
        val request = TaskCreateRequest(description = description)
        return apiService.createTask(request)
    }
}
