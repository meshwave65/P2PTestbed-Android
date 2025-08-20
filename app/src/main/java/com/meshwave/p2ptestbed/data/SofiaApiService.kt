// app/src/main/java/com/meshwave/p2ptestbed/data/SofiaApiService.kt
// VERSÃO 1.1.0 - Adiciona o endpoint para buscar os Tópicos de Missão.

package com.meshwave.p2ptestbed.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Interface que define todos os endpoints da API SOFIA.
 * O Retrofit usará esta interface para gerar o código de rede necessário.
 */
interface SofiaApiService {

    /**
     * Envia uma requisição para criar uma nova tarefa no sistema.
     * @param taskRequest O corpo da requisição, contendo a descrição da tarefa.
     * @return Uma resposta que, em caso de sucesso, conterá os detalhes da tarefa criada.
     */
    @POST("api/v1/tasks/" )
    suspend fun createTask(@Body taskRequest: TaskCreateRequest): Response<TaskCreateResponse>

    // --- [NOVO ENDPOINT ADICIONADO AQUI] ---

    /**
     * Busca a lista de Tópicos de Missão para um usuário específico.
     * @param clientId O ID do cliente para o qual buscar os tópicos.
     * @return Uma resposta que, em caso de sucesso, conterá uma lista de MissionTopicDto.
     */
    @GET("api/v1/users/{client_id}/topics")
    suspend fun getMissionTopics(@Path("client_id") clientId: String): Response<List<MissionTopicDto>>

}

