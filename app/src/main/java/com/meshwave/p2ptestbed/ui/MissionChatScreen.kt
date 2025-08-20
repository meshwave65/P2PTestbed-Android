package com.meshwave.p2ptestbed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.meshwave.p2ptestbed.data.Interaction

@Composable
fun MissionChatScreen(
    interactions: List<Interaction>,
    onSendMessage: (String) -> Unit
) {
    var userPrompt by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Área de Chat
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            reverseLayout = true // Mostra as mensagens mais recentes no final
        ) {
            items(interactions.reversed()) { interaction ->
                InteractionBubble(interaction)
            }
        }

        // Área de Entrada de Texto
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userPrompt,
                onValueChange = { userPrompt = it },
                label = { Text("Digite sua mensagem...") },
                modifier = Modifier.weight(1f),
                maxLines = 5
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (userPrompt.isNotBlank()) {
                        onSendMessage(userPrompt)
                        userPrompt = ""
                    }
                },
                modifier = Modifier.size(56.dp),
                colors = IconButtonDefaults.filledIconButtonColors()
            ) {
                Icon(Icons.Default.Send, contentDescription = "Enviar")
            }
        }
    }
}

@Composable
fun InteractionBubble(interaction: Interaction) {
    val isFromUser = interaction.author == "user"
    val alignment = if (isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    val backgroundColor = if (isFromUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Text(
            text = interaction.text,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .widthIn(max = 300.dp) // Limita a largura do balão
        )
    }
}
