package com.example.ai_rpg_project.Screens.ChatPageScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ai_rpg_project.Utils.MessageInput
import com.example.ai_rpg_project.Utils.MessageList
import com.example.ai_rpg_project.Utils.TopBar


@Composable
fun ChatPage(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),

    name: String = "",
    setting: String = "",
    charName: String = "",
    strength: Int = 0,
    defense: Int = 0,
    speed: Int = 0,
    specialItem: String = ""
) {
    val hp by viewModel.hp
    val context = LocalContext.current
    val showSaveDialog by viewModel.showSaveDialog
    val isLoadedGame by viewModel.isLoadedGame



    LaunchedEffect(key1 = isLoadedGame) {

        if (isLoadedGame) {
            val savedChatHistory = viewModel.getChatHistoryAsString()
            if (savedChatHistory.isNotBlank()) {
                val continuePrompt = "Continue from here\n$savedChatHistory"
                viewModel.sendMessage(continuePrompt)
            }
            viewModel.resetLoadedGameFlag()
        } else if (viewModel.messageList.isEmpty()) {
            viewModel.startNewGame(name, setting, charName, strength, defense, speed, specialItem)
        }

    }


    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissSaveGameDialog() },
            title = { Text("Save Game") },
            text = { Text("Do you want to save your current game progress?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveGame(onSuccess = {
                            Toast.makeText(
                                context,
                                "Game saved successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, onError = { e ->
                            Toast.makeText(
                                context,
                                "Failed to save: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.dismissSaveGameDialog() }) {
                    Text("Cancel")
                }
            })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2A1B3D))
    ) {
        Column(
            modifier = modifier
        ) {
            TopBar(hp = hp)
            MessageList(
                modifier = Modifier.weight(1f), messageList = viewModel.messageList
            )
            MessageInput(
                onMessageSend = { viewModel.sendMessage(it) },
                onSaveRequest = { viewModel.showSaveGameDialog() })
        }
    }
}


