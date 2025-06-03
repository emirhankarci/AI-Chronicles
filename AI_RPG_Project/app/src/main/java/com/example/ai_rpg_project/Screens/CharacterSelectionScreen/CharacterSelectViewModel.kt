package com.example.ai_rpg_project.Screens.CharacterSelectionScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.ai_rpg_project.R
import com.example.ai_rpg_project.data.CharacterDetails
import com.example.ai_rpg_project.data.CharacterStats
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject




@HiltViewModel
class CharacterSelectionViewModel @Inject constructor() : ViewModel() {


    var selectedCharacter by mutableStateOf<String?>(null)
        private set


    var selectedCharacterStats by mutableStateOf<CharacterStats?>(null)
        private set


    private val _expandedStates = mutableStateMapOf<String, Boolean>()
    val expandedStates: Map<String, Boolean> = _expandedStates

    val characters = listOf(
        "Thief" to CharacterDetails(
            iconRes = R.drawable.thief,
            backgroundColor = Color(0xFF1C1C3C),
            stats = CharacterStats(strength = 70, defense = 50, speed = 90)
        ), "King" to CharacterDetails(
            iconRes = R.drawable.king,
            backgroundColor = Color(0xFFFFD700),
            stats = CharacterStats(strength = 90, defense = 80, speed = 60)
        ), "Wizard" to CharacterDetails(
            iconRes = R.drawable.wizard,
            backgroundColor = Color(0xFF4169E1),
            stats = CharacterStats(strength = 60, defense = 50, speed = 80)
        ), "Warrior" to CharacterDetails(
            iconRes = R.drawable.warrior,
            backgroundColor = Color(0xFF8B0000),
            stats = CharacterStats(strength = 85, defense = 70, speed = 70)
        ), "Knight" to CharacterDetails(
            iconRes = R.drawable.knight,
            backgroundColor = Color.Gray,
            stats = CharacterStats(strength = 80, defense = 90, speed = 50)
        ), "Prince" to CharacterDetails(
            iconRes = R.drawable.prince,
            backgroundColor = Color.Magenta,
            stats = CharacterStats(strength = 75, defense = 65, speed = 85)
        ), "Peasant" to CharacterDetails(
            iconRes = R.drawable.peasant,
            backgroundColor = Color.Blue,
            stats = CharacterStats(strength = 60, defense = 40, speed = 50)
        ), "Vampire" to CharacterDetails(
            iconRes = R.drawable.vampire,
            backgroundColor = Color(0xFF8B0000),
            stats = CharacterStats(strength = 95, defense = 60, speed = 90)
        )
    )


    fun onCharacterCardClicked(charName: String, stats: CharacterStats) {

        if (selectedCharacter == charName) {

            selectedCharacter = null
            selectedCharacterStats = null

            _expandedStates[charName] = false
        } else {

            _expandedStates.keys.forEach { key -> _expandedStates[key] = false }


            selectedCharacter = charName
            selectedCharacterStats = stats


            _expandedStates[charName] = true
        }
    }


    fun onSelectCharacterClicked(
        name: String, setting: String, onNavigate: (String, String, String, CharacterStats) -> Unit
    ) {
        selectedCharacter?.let { charName ->
            selectedCharacterStats?.let { stats ->
                onNavigate(name, setting, charName, stats)
            }
        }
    }
}