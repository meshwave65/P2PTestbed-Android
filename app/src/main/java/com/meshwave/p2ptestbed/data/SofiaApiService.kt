// app/src/main/java/com/meshwave/p2ptestbed/data/SofiaApiService.kt
// VERSÃO 1.0.0 - Define os endpoints da API SOFIA para o Retrofit.

package com.meshwave.p2ptestbed.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

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

    // Futuramente, adicionaremos outros endpoints aqui, como:
    // @GET("api/v1/tasks/{id}")
    // suspend fun getTaskStatus(@Path("id") taskId: Int): Response<TaskStatusResponse>
}
