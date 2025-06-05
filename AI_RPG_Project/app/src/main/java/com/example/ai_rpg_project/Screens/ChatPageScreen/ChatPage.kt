package com.example.ai_rpg_project.Screens.ChatPageScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigateToMainMenu: () -> Unit,
) {
    val hp by viewModel.hp
    val context = LocalContext.current
    val showSaveDialog by viewModel.showSaveDialog


    BackHandler(enabled = true) { }




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
            modifier = Modifier
        ) {
            TopBar(hp = hp, onMainMenuClicked = onNavigateToMainMenu)
            MessageList(
                modifier = Modifier.weight(1f), messageList = viewModel.messageList
            )
            MessageInput(
                onMessageSend = { viewModel.sendMessage(it) },
                onSaveRequest = { viewModel.showSaveGameDialog() })
        }
    }
}


