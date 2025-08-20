// app/src/main/java/com/meshwave/p2ptestbed/data/model/TaskDtos.kt
// VERSÃO 1.0.0 - Modelos de dados para comunicação com a API SOFIA.

package com.meshwave.p2ptestbed.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) para enviar a requisição de criação de uma nova tarefa.
 * Este objeto será convertido para o JSON que a API espera.
 */
data class TaskCreateRequest(
    val description: String,
    @SerializedName("priority_id") // Garante que o nome no JSON seja "priority_id"
    val priorityId: Int = 1, // Default: Prioridade Normal
    @SerializedName("status_id")
    val statusId: Int = 1, // Default: Status "Pendente"
    @SerializedName("wbs_tag")
    val wbsTag: String = "MobileApp" // Identifica que a tarefa veio do app
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
    val createdAt: String // Recebemos como String para simplicidade
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
