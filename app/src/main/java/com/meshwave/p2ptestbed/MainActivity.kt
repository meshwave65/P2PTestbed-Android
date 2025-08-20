// app/src/main/java/com/meshwave/p2ptestbed/ui/MainActivity.kt
// VERSÃO 0.2.2 - Aumenta a altura do campo de texto da missão.

package com.meshwave.p2ptestbed.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.meshwave.p2ptestbed.ui.theme.P2PTestbedTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P2PTestbedTheme {
                SofiaScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SofiaScreen(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsState()
    var userPrompt by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MeshWave - SOFIA") },
                actions = {
                    Text(
                        text = "v1.0-beta",
                        modifier = Modifier.padding(end = 16.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Área de Resposta
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                items(state.logMessages) { message ->
                    Text(text = message)
                }
            }

            // ID da Missão e Botão de Download
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = state.missionId ?: "Nenhuma missão ativa",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Button(onClick = { /* TODO */ }, enabled = state.isDownloadEnabled) {
                    Text("Download")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- [MUDANÇA APLICADA AQUI] ---
            // Campo de Texto para a Missão
            OutlinedTextField(
                value = userPrompt,
                onValueChange = { userPrompt = it },
                label = { Text("Digite sua missão ou consulta...") },
                modifier = Modifier.fillMaxWidth(),
                // Garante que o campo tenha altura para 3 linhas e possa crescer até 6.
                minLines = 3,
                maxLines = 6
            )
            // --- [FIM DA MUDANÇA] ---

            Spacer(modifier = Modifier.height(8.dp))

            // Botões de Ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { /* TODO */ }) {
                    Text("Upload")
                }
                Row {
                    Button(
                        onClick = { userPrompt = "" },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Limpar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (userPrompt.isNotBlank()) {
                                viewModel.sendMission(userPrompt)
                            }
                        },
                        enabled = !state.isMissionRunning
                    ) {
                        Text("Enviar")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    P2PTestbedTheme {
        SofiaScreen(viewModel = MainViewModel())
    }
}
