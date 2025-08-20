// app/src/main/java/com/meshwave/p2ptestbed/data/ApiClient.kt
// VERSÃO 1.0.1 - Estrutura de pacote corrigida.

package com.meshwave.p2ptestbed.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://sofia-api.meshwave.com.br/"

    private val loggingInterceptor = HttpLoggingInterceptor( ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: SofiaApiService by lazy {
        retrofit.create(SofiaApiService::class.java)
    }
}
