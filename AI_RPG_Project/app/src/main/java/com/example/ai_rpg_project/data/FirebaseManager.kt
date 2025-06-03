package com.example.ai_rpg_project.data

import com.example.ai_rpg_project.data.GameSave
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.get

object FirebaseManager {
    private val db = Firebase.firestore
    private const val COLLECTION_SAVES = "session"

    fun saveGame(gameSave: GameSave, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val saveMap = hashMapOf(
            "id" to gameSave.id,
            "playerName" to gameSave.playerName,
            "selectedTheme" to gameSave.selectedTheme,
            "characterName" to gameSave.characterName,
            "strength" to gameSave.strength,
            "defense" to gameSave.defense,
            "speed" to gameSave.speed,
            "hp" to gameSave.hp,
            "messages" to gameSave.messages.map { message ->
                hashMapOf(
                    "message" to message.message,
                    "role" to message.role
                )
            },
            "timestamp" to gameSave.timestamp
        )

        db.collection(COLLECTION_SAVES)
            .document(gameSave.id)
            .set(saveMap)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun loadGame(gameId: String, onSuccess: (GameSave) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection(COLLECTION_SAVES)
            .document(gameId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    try {
                        // Extract message list
                        val messagesData = document.get("messages") as? List<*> ?: emptyList<Any>()
                        val messageList = messagesData.mapNotNull { item ->
                            val messageMap = item as? Map<*, *> ?: return@mapNotNull null
                            val message = messageMap["message"] as? String ?: ""
                            val role = messageMap["role"] as? String ?: ""

                            println("Loaded message: $message, Role: $role")
                            MessageModel(message, role)
                        }

                        val gameSave = GameSave(
                            id = document.getString("id") ?: "",
                            playerName = document.getString("playerName") ?: "",
                            selectedTheme = document.getString("selectedTheme") ?: "",
                            characterName = document.getString("characterName") ?: "",
                            strength = document.getLong("strength")?.toInt() ?: 0,
                            defense = document.getLong("defense")?.toInt() ?: 0,
                            speed = document.getLong("speed")?.toInt() ?: 0,
                            hp = document.getLong("hp")?.toInt() ?: 100,
                            messages = messageList, // Make sure this is populated
                            timestamp = document.getTimestamp("timestamp")
                                ?: Timestamp.Companion.now()
                        )

                        onSuccess(gameSave)
                    } catch (e: Exception) {
                        onFailure(e)
                    }
                } else {
                    onFailure(Exception("Game save not found"))
                }
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getAllSavedGames(onSuccess: (List<GameSave>) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection(COLLECTION_SAVES)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val gameList = mutableListOf<GameSave>()

                for (document in documents) {
                    try {
                        val gameId = document.getString("id") ?: ""
                        val playerName = document.getString("playerName") ?: ""
                        val selectedTheme = document.getString("selectedTheme") ?: ""
                        val characterName = document.getString("characterName") ?: ""
                        val hp = document.getLong("hp")?.toInt() ?: 100
                        val strength = document.getLong("strength")?.toInt() ?: 0
                        val defense = document.getLong("defense")?.toInt() ?: 0
                        val speed = document.getLong("speed")?.toInt() ?: 0
                        val timestamp = document.getTimestamp("timestamp") ?: Timestamp.Companion.now()

                        // Extract message list - add this code
                        val messagesData = document.get("messages") as? List<*> ?: emptyList<Any>()
                        val messageList = messagesData.mapNotNull { item ->
                            val messageMap = item as? Map<*, *> ?: return@mapNotNull null
                            val message = messageMap["message"] as? String ?: ""
                            val role = messageMap["role"] as? String ?: ""
                            MessageModel(message, role)
                        }

                        gameList.add(
                            GameSave(
                                id = gameId,
                                playerName = playerName,
                                selectedTheme = selectedTheme,
                                characterName = characterName,
                                hp = hp,
                                strength = strength,
                                defense = defense,
                                speed = speed,
                                timestamp = timestamp,
                                messages = messageList // Add this field
                            )
                        )
                    } catch (e: Exception) {
                        // Skip this document if there's an error
                        continue
                    }
                }

                onSuccess(gameList)
            }
            .addOnFailureListener { onFailure(it) }
    }
}