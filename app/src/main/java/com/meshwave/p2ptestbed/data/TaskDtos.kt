// app/src/main/java/com/meshwave/p2ptestbed/data/TaskDtos.kt
// VERSÃO 1.1.0 - Adiciona o DTO para a lista de Tópicos de Missão.

package com.meshwave.p2ptestbed.data

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) para enviar a requisição de criação de uma nova tarefa.
 * Este objeto será convertido para o JSON que a API espera.
 */
data class TaskCreateRequest(
    val description: String,
    @SerializedName("priority_id")
    val priorityId: Int = 1, // Default: Prioridade Normal
    @SerializedName("status_id")
    val statusId: Int = 1 // Default: Status "Pendente"
)

/**
 * Data Transfer Object (DTO) para receber a resposta da API após a criação da tarefa.
 * O Retrofit usará este modelo para converter o JSON de resposta em um objeto Kotlin.
 */
data class TaskCreateResponse(
    @SerializedName("task_id")
    val taskId: Int,
    val description: String?,
    val status: TaskStatus,
    val priority: TaskPriority,
    @SerializedName("created_at")
    val createdAt: String
)

data class TaskStatus(
    @SerializedName("status_id")
    val statusId: Int,
    val name: String
)

data class TaskPriority(
    @SerializedName("priority_id")
    val priorityId: Int,
    val name: String
)

// --- [NOVO DTO ADICIONADO AQUI] ---

/**
 * Data Transfer Object (DTO) para receber um item da lista de Tópicos de Missão.
 * Corresponde ao schema `MissionTopic` da API.
 */
data class MissionTopicDto(
    @SerializedName("task_id")
    val taskId: Int,

    @SerializedName("task_code")
    val taskCode: String,

    @SerializedName("task_name")
    val taskName: String
)

