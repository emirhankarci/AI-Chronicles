package com.example.ai_rpg_project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class ChatViewModel : ViewModel() {




    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash-002",
        apiKey = Constants.apiKey
    )


    private val _hp = mutableIntStateOf(100) // HP starts at 100
    val hp: State<Int> = _hp

    // Function to extract HP from AI messages
    private fun extractHpFromMessage(message: String): Int {
        val regex = """You have (?:\d+\s*-\s*\d+\s*=\s*)?(\d+) health(?: points)? left""".toRegex()
        val matches = regex.findAll(message).toList() // Find all matches

        val lastMatch = matches.lastOrNull()?.groupValues?.get(1)?.toIntOrNull()
        return lastMatch?.coerceIn(0, 100) ?: _hp.value
    }




    // Function to update HP based on AI response
    fun updateHpFromMessage(message: String) {
        _hp.value = extractHpFromMessage(message)
    }


    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                // Prepend the RPG game master prompt as the first message in history
                val chat = generativeModel.startChat(
                    history = listOf(
                        content("model") {
                            text("You are the game master of this RPG. Guide the user on an exciting adventure."  +
                                    "Create the beginning of the story. After setting up the scene, provide three options for the user to choose from." +
                                    "Based on the user's choice, craft the next part of the story with new options, and continue from there."  +
                                    "If the user asks an irrelevant question, type 'I can't answer it right now.'  " +

                                    "The player has Strength, Defense, and Speed stats, which affect combat and interactions." +
                                    "- **Strength** determines the player's attack power. Higher strength means stronger attacks.  " +
                                    "- **Defense** reduces incoming damage. A higher defense makes the player take less damage from enemies." +
                                    "- **Speed** influences dodging and reaction time. A higher speed allows the player to avoid some attacks or strike first. " +

                                    "Give the player a total of ${_hp.value} health points and enemies 75 health points. " +
                                    "From time to time, the player will encounter a monster or a human enemy." +
                                    "These enemies should deal damage to the player based on a number you determine. " +
                                    "This damage value should vary with each attack, depending on different factors, including the player's Defense and Speed. " +
                                    "Similarly, the player should also deal damage to these enemies based on their Strength. " +
                                    "If the total damage the player takes exceeds their health points, end the game or introduce surprise elements—for example, " +
                                    "another hero arriving at the last moment to help or the enemy getting distracted." +
                                    "And write 'You have ${_hp.value} left' on each message.")

                        }
                    ) + messageList.map {
                        content(it.role) { text(it.message) }
                    }
                )

                messageList.add(MessageModel(question, "user"))
                messageList.add(MessageModel("Typing...", "model"))

                val response = chat.sendMessage(question)
                messageList.removeLast()
                messageList.add(MessageModel(response.text.toString(), "model"))


                updateHpFromMessage(response.text.toString())

            } catch (e: Exception) {
                messageList.removeLast()
                messageList.add(MessageModel("Error: ${e.message}", "model"))
            }
        }
    }



}
