package com.example.ai_rpg_project.Screens.CreateStoryScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateStoryScreenViewModel @Inject constructor() : ViewModel() {


    var story by mutableStateOf("")
        private set

    var mainCharacter by mutableStateOf("")
        private set

    var sideCharacters by mutableStateOf("")
        private set

    var specialItem by mutableStateOf("")
        private set

    var CSstrength by mutableStateOf("")
        private set

    var CSdefense by mutableStateOf("")
        private set

    var CSspeed by mutableStateOf("")
        private set


    fun onStoryChange(newValue: String) {
        story = newValue
    }

    fun onMainCharacterChange(newValue: String) {
        mainCharacter = newValue
    }

    fun onSideCharactersChange(newValue: String) {
        sideCharacters = newValue
    }

    fun onSpecialItemChange(newValue: String) {
        specialItem = newValue
    }

    fun onStrengthChange(newValue: String) {
        CSstrength = newValue
    }

    fun onDefenseChange(newValue: String) {
        CSdefense = newValue
    }

    fun onSpeedChange(newValue: String) {
        CSspeed = newValue
    }


    fun onSubmitClicked(onNavigateToChatPage: (String) -> Unit) {

        val encodedStory = Uri.encode(story)
        val encodedMain = Uri.encode(mainCharacter)
        val encodedSide = Uri.encode(sideCharacters)
        val encodedItem = Uri.encode(specialItem)
        val encodedCSStrength = Uri.encode(CSstrength)
        val encodedCSDefense = Uri.encode(CSdefense)
        val encodedCSSpeed = Uri.encode(CSspeed)

        val route = "ChatPageStory/" +
                "$encodedStory/" +
                "$encodedMain/" +
                "$encodedSide/" +
                "$encodedItem/" +
                "$encodedCSStrength/" +
                "$encodedCSDefense/" +
                "$encodedCSSpeed"

        onNavigateToChatPage(route)
    }
}