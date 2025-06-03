package com.example.ai_rpg_project.data

import com.google.firebase.Timestamp
import java.util.UUID

data class GameSave(
    val id: String = UUID.randomUUID().toString(),
    val playerName: String = "",
    val selectedTheme: String = "",
    val characterName: String = "",
    val strength: Int = 0,
    val defense: Int = 0,
    val speed: Int = 0,
    val hp: Int = 100,
    val messages: List<MessageModel> = emptyList(),
    val timestamp: Timestamp = Timestamp.Companion.now()
)