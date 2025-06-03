package com.example.ai_rpg_project.Screens.ChatPageScreen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai_rpg_project.Utils.Constants
import com.example.ai_rpg_project.data.FirebaseManager
import com.example.ai_rpg_project.data.GameSave
import com.example.ai_rpg_project.data.MessageModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.util.UUID


class ChatViewModel : ViewModel() {


    private val _isLoadedGame = mutableStateOf(false)
    val isLoadedGame: State<Boolean> = _isLoadedGame

    val messageList = mutableStateListOf<MessageModel>()

    private val _hp = mutableIntStateOf(100)
    val hp: State<Int> = _hp


    private val _showSaveDialog = mutableStateOf(false)
    val showSaveDialog: State<Boolean> = _showSaveDialog


    private var playerName: String = ""
    private var theme: String = ""
    private var characterName: String = ""
    private var strength: Int = 0
    private var defense: Int = 0
    private var speed: Int = 0
    private var specialItem: String = ""


    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash", apiKey = Constants.apiKey
    )


    private fun extractHpFromMessage(message: String): Int {
        val regex =
            """You have (?:\d+\s*-\s*\d+\s*=\s*)?(\d+)(?: health(?: points)?)? left\.?""".toRegex()
        val lastMatch = regex.findAll(message).toList().lastOrNull()

        if (lastMatch != null) {
            val extractedValue = lastMatch.groupValues.getOrNull(1)
            Log.d("HP_Check", "Regex match found. Group 1 value: $extractedValue")
            val hpValue = extractedValue?.toIntOrNull()
            if (hpValue != null) {
                val coercedHp = hpValue.coerceIn(0, 100)
                Log.d("HP_Check", "Parsed and coerced HP: $coercedHp")
                return coercedHp
            } else {
                Log.d(
                    "HP_Check",
                    "Regex match found, but Group 1 value '$extractedValue' could not be parsed to Int."
                )
                return _hp.value
            }
        } else {
            Log.d("HP_Check", "No regex match found in message.")
            return _hp.value
        }
    }


    private fun updateHpFromMessage(message: String) {
        _hp.value = extractHpFromMessage(message)
    }


    fun getChatHistoryAsString(): String {
        return messageList.filter { it.message != "Typing..." }
            .joinToString("\n") { "${it.role.capitalize()}: ${it.message}" }
    }


    fun showSaveGameDialog() {
        _showSaveDialog.value = true
    }

    fun dismissSaveGameDialog() {
        _showSaveDialog.value = false
    }


    fun saveGame(onSuccess: () -> Unit, onError: (Exception) -> Unit) {

        val gameSave = GameSave(
            id = UUID.randomUUID().toString(),
            playerName = playerName,
            selectedTheme = theme,
            characterName = characterName,
            strength = strength,
            defense = defense,
            speed = speed,
            hp = hp.value,
            messages = messageList.toList(),
            timestamp = Timestamp.now()
        )

        FirebaseManager.saveGame(gameSave, onSuccess = {
            Log.d("ChatViewModel", "Game saved successfully!")
            dismissSaveGameDialog()
            onSuccess()
        }, onFailure = { error ->
            Log.e("ChatViewModel", "Failed to save game: ${error.message}")
            dismissSaveGameDialog()
            onError(error)
        })
    }

    fun loadGame(gameId: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        FirebaseManager.loadGame(gameId, onSuccess = { gameSave ->

            messageList.clear()
            messageList.addAll(gameSave.messages)
            _hp.value = gameSave.hp
            _isLoadedGame.value = true


            playerName = gameSave.playerName
            theme = gameSave.selectedTheme
            characterName = gameSave.characterName
            strength = gameSave.strength
            defense = gameSave.defense
            speed = gameSave.speed


            Log.d(
                "ChatViewModel",
                "Game loaded successfully. Messages: ${messageList.size}, HP: ${_hp.value}"
            )
            onSuccess()
        }, onFailure = { exception ->
            Log.e("ChatViewModel", "Failed to load game: ${exception.message}")
            onError(exception)
        })
    }


    fun resetLoadedGameFlag() {
        _isLoadedGame.value = false
    }


    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                if (question.isBlank()) return@launch

                messageList.add(MessageModel(question, "user"))
                messageList.add(MessageModel("Typing...", "model"))


                val gameMasterPrompt = Constants.getGameMasterPrompt(_hp.value)

                val chat = generativeModel.startChat(
                    history = listOf(
                        content("model") {
                            text(gameMasterPrompt)
                        }
                    ) + messageList.filter { it.message != "Typing..." }.map {
                        content(it.role) { text(it.message) }
                    }
                )

                val response = chat.sendMessage(question)
                messageList.removeLast()
                val modelResponse = response.text.toString()
                messageList.add(MessageModel(modelResponse, "model"))

                updateHpFromMessage(modelResponse)

            } catch (e: Exception) {
                messageList.removeLast()
                messageList.add(MessageModel("Error: ${e.message}", "model"))
                Log.e("ChatViewModel", "Error sending message: ${e.message}", e)
            }
        }
    }


    fun startNewGame(
        name: String,
        setting: String,
        charName: String,
        strength: Int,
        defense: Int,
        speed: Int,
        specialItem: String = ""
    ) {

        this.playerName = name
        this.theme = setting
        this.characterName = charName
        this.strength = strength
        this.defense = defense
        this.speed = speed
        this.specialItem = specialItem


        messageList.clear()
        _hp.value = 100

        val isFromCreateStory = specialItem.isNotBlank()

        val initialMessage = if (isFromCreateStory) {
            "Welcome to your custom adventure! " + "Your story is set in: $setting. " + "You are playing as: $name. " + "Your adventure includes side characters: $charName. " + "You possess a special item: $specialItem. " + "Your stats are: strength: $strength, defense: $defense, speed: $speed. Let's begin your tale!"
        } else {
            "Welcome! Your name is $name. " + "You have entered the $setting setting and you are a $charName. " + "You have $strength strength, $defense defense, and $speed speed."
        }
        sendMessage(initialMessage)
    }

}