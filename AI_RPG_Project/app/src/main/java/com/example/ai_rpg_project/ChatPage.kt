package com.example.ai_rpg_project

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai_rpg_project.ui.theme.Purple80



@Composable
fun ChatPage(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel,
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
    var showSaveDialog by remember { mutableStateOf(false) }

    // Check if this is a fresh game (messageList is empty)
    val isFreshGame = viewModel.messageList.isEmpty()

    // Add save game dialog
    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Save Game") },
            text = { Text("Do you want to save your current game progress?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveGame(
                            playerName = name,
                            theme = setting,
                            characterName = charName,
                            strength = strength,
                            defense = defense,
                            speed = speed,
                            onSuccess = {
                                Toast.makeText(context, "Game saved successfully!", Toast.LENGTH_SHORT).show()
                                showSaveDialog = false
                            },
                            onError = { e ->
                                Toast.makeText(context, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                                showSaveDialog = false
                            }
                        )
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { showSaveDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2A1B3D)) // Dark purple background
    ) {
        Column(
            modifier = modifier
        ) {
            TopBar(hp = hp)
            MessageList(
                modifier = Modifier.weight(1f),
                messageList = viewModel.messageList
            )
            MessageInput(
                onMessageSend = { viewModel.sendMessage(it) },
                onSaveRequest = { showSaveDialog = true }
            )
        }
    }

    // Only send initial message for a fresh game
    LaunchedEffect(Unit) {
        if (isFreshGame) {
            val isFromCreateStory = specialItem.isNotBlank()

            if (isFromCreateStory) {
                // Custom story path
                val initialMessage = "Welcome to your custom adventure! " +
                        "Your story is set in: $setting. " +
                        "You are playing as: $name. " +
                        "Your adventure includes side characters: $charName. " +
                        "You possess a special item: $specialItem. " +
                        "Your stats are: strength: $strength, defense: $defense, speed: $speed. Let's begin your tale!"
                viewModel.sendMessage(initialMessage)
            } else {
                // Character selection path (original path)
                val initialMessage = "Welcome! Your name is $name. " +
                        "You have entered the $setting setting and you are a $charName. " +
                        "You have $strength strength, $defense defense, and $speed speed."
                viewModel.sendMessage(initialMessage)
            }
        }
    }
}



@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>) {
    if (messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription = "Icon",
                tint = Purple80,
            )
            Text(text = "Ask me anything", fontSize = 22.sp)
        }
    } else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(12.dp) // Ensures spacing between messages
        ) {
            items(messageList.reversed()) { message ->
                MessageRow(messageModel = message)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp), // Additional spacing between messages
        horizontalArrangement = if (isModel) Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        if (isModel) {
            Image(
                painter = painterResource(id = R.drawable.selectname), // Replace with actual resource
                contentDescription = "Model Avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (isModel) Color(0xFF3A1C58) else Color(0xFF3E2C68),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            SelectionContainer {
                Text(
                    text = messageModel.message,
                    fontWeight = FontWeight.W500,
                    color = Color.White
                )
            }
        }

        if (!isModel) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.skulllll), // Replace with actual resource
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }
    }
}





@Composable
fun MessageInput(onMessageSend: (String) -> Unit, onSaveRequest: () -> Unit) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF44318D).copy(alpha = 0.3f))
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Change this to be a Save button
        IconButton(onClick = onSaveRequest) {
            Icon(
                imageVector = Icons.Default.Add, // Change to Save icon
                contentDescription = "Save Game",
                tint = Color.White
            )
        }

        // Text input field
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            placeholder = {
                Text("What would you like to do?", color = Color.White.copy(alpha = 0.5f))
            },
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.1f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                disabledContainerColor = Color.White.copy(alpha = 0.1f),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledTextColor = Color.White.copy(alpha = 0.5f)
            )
        )

        // Send button
        IconButton(onClick = {
            if (message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(
                imageVector = Icons.Outlined.Send,
                contentDescription = "Send",
                tint = Color.White
            )
        }
    }
}


@Composable
fun TopBar(hp: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF44318D).copy(alpha = 0.3f)) // 30% opacity
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.skulllll), // Replace with actual resource
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Shadow Mage", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFD97706), CircleShape)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("15", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                // Show only HP number
                Text("❤ $hp", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            IconButton(onClick = { /* Menu action */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24), // Replace with actual resource
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
fun ProgressBar(label: String, progress: Float, color: Color) {
    Column {
        Text(label, color = Color.White, fontSize = 12.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color.DarkGray, RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp)
                    .background(color, RoundedCornerShape(4.dp))
            )
        }
    }
}
