package com.example.ai_rpg_project.Screens.NameScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import javax.inject.Inject

@HiltViewModel
class NameSelectViewModel @Inject constructor() : ViewModel() {


    var name by mutableStateOf("")
        private set

    fun onNameChanged(newName: String) {
        name = newName
    }


    fun onBeginJourneyClicked(onNavigate: (String) -> Unit) {
        if (name.isNotBlank()) {
            onNavigate("EpicRealmsUI/$name")
        }
    }
}