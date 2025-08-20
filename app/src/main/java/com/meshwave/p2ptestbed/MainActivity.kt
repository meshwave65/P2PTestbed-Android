package com.meshwave.p2ptestbed.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.meshwave.p2ptestbed.data.MissionTopic
import com.meshwave.p2ptestbed.ui.theme.P2PTestbedTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P2PTestbedTheme {
                SofiaApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SofiaApp(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MeshWave - SOFIA") },
                actions = {
                    Text(
                        text = "v1.0",
                        modifier = Modifier.padding(end = 16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            if (uiState.selectedTopic == null) {
                FloatingActionButton(onClick = { /* TODO: Lógica para criar novo tópico */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Novo Tópico")
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (uiState.selectedTopic == null) {
                // TELA DE LISTA DE TÓPICOS
                // --- [AJUSTE DE ESTILO E TEXTO APLICADO AQUI] ---
                Text(
                    text = "TÓPICOS", // Alterado para caixa alta
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), // Estilo ajustado
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                // --- [FIM DA MUDANÇA] ---
                Divider()
                MissionTopicList(
                    topics = uiState.missionTopics,
                    onTopicClick = { topic -> viewModel.selectTopic(topic) }
                )
            } else {
                // TELA DE CHAT
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.deselectTopic() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = uiState.selectedTopic!!.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Divider()
                MissionChatScreen(
                    interactions = uiState.currentInteractions,
                    onSendMessage = { message ->
                        viewModel.sendInteraction(message, uiState.selectedTopic!!.id)
                    }
                )
            }
        }
    }
}

// O restante do arquivo (MissionTopicList, MissionTopicItem, Previews) permanece o mesmo.

@Composable
fun MissionTopicList(topics: List<MissionTopic>, onTopicClick: (MissionTopic) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(topics) { topic ->
            MissionTopicItem(topic = topic, onClick = { onTopicClick(topic) })
            Divider()
        }
    }
}

@Composable
fun MissionTopicItem(topic: MissionTopic, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = topic.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = topic.lastInteraction,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = topic.timestamp, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true, name = "Tela de Tópicos")
@Composable
fun TopicsScreenPreview() {
    P2PTestbedTheme {
        val viewModel = MainViewModel()
        SofiaApp(viewModel = viewModel)
    }
}

@Preview(showBackground = true, name = "Tela de Chat")
@Composable
fun ChatScreenPreview() {
    P2PTestbedTheme {
        val viewModel = MainViewModel()
        viewModel.selectTopic(MissionTopic("PREVIEW-01", "Relatório de Vendas Q3", "...", "..."))
        SofiaApp(viewModel = viewModel)
    }
}
