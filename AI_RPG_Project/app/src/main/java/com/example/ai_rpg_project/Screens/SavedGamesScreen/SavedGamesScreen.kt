package com.example.ai_rpg_project.Screens.SavedGamesScreen

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.ai_rpg_project.Screens.ChatPageScreen.ChatViewModel
import com.example.ai_rpg_project.data.FirebaseManager
import com.example.ai_rpg_project.data.GameSave


@Composable
fun SavedGamesScreen(navController: NavHostController, viewModel: ChatViewModel) {
    val context = LocalContext.current
    var savedGames by remember { mutableStateOf<List<GameSave>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        FirebaseManager.getAllSavedGames(
            onSuccess = { games ->
                savedGames = games
                isLoading = false
            },
            onFailure = { e ->
                errorMessage = e.message
                isLoading = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1D3C))
            .padding(16.dp)
    ) {
        Text(
            text = "SAVED GAMES",
            color = Color(0xFFFFD700),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFFFD700))
            }
        } else if (errorMessage != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        } else if (savedGames.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved games found",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(savedGames) { game ->
                    SavedGameItem(
                        game = game,
                        onClick = {
                            viewModel.loadGame(
                                gameId = game.id,
                                onSuccess = {
                                    Toast.makeText(context, "Game loaded successfully!", Toast.LENGTH_SHORT).show()
                                    navController.navigate("ChatPage/${game.playerName}/${game.selectedTheme}/${game.characterName}/${game.strength}/${game.defense}/${game.speed}")
                                },
                                onError = { e ->
                                    Toast.makeText(context, "Failed to load: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                }
            }


        }
    }
}

@Composable
fun SavedGameItem(game: GameSave, onClick: () -> Unit) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${game.playerName} - ${game.characterName}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = game.selectedTheme,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
                Text(
                    text = "HP: ${game.hp} • STR: ${game.strength} • DEF: ${game.defense} • SPD: ${game.speed}",
                    color = Color(0xFFFF6B6B),
                    fontSize = 14.sp
                )
                Text(
                    text = "Saved: ${dateFormat.format(game.timestamp.toDate())}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Load Game",
                tint = Color(0xFFFFD700)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSavedGamesScreen() {
    val navController = rememberNavController()
    val dummyGames = listOf(
        GameSave(
            id = "1",
            playerName = "Alex",
            characterName = "CyberKnight",
            selectedTheme = "Neo-Tokyo",
            hp = 100,
            strength = 80,
            defense = 70,
            speed = 90,
            timestamp = Timestamp.now()
        ),
        GameSave(
            id = "2",
            playerName = "Riley",
            characterName = "ShadowHacker",
            selectedTheme = "Dystopian Future",
            hp = 120,
            strength = 85,
            defense = 65,
            speed = 75,
            timestamp = Timestamp.now()
        )
    )

    val fakeViewModel = ChatViewModel() // Replace with a mock if needed

    SavedGamesScreen(navController, fakeViewModel)
}
