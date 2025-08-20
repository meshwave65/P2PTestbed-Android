package com.meshwave.p2ptestbed.data

// Modelo de dados para representar um "Tópico de Missão" (um chat)
data class MissionTopic(
    val id: String, // Corresponderá ao task_id do nosso "Tópico" (Nível 1)
    val title: String,
    val lastInteraction: String,
    val timestamp: String
)

// Modelo de dados para representar uma "Interação" (uma mensagem no chat)
data class Interaction(
    val id: String, // Corresponderá ao task_id da "Interação" (Nível 2)
    val text: String,
    val author: String // "user" ou "sofia"
)
