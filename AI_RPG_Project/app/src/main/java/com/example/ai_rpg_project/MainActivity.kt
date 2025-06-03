package com.example.ai_rpg_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.example.ai_rpg_project.Screens.ChatPageScreen.ChatViewModel
import com.example.ai_rpg_project.Screens.NavigationScreen.NavigationSetup
import com.example.ai_rpg_project.ui.theme.AI_RPG_ProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AI_RPG_ProjectTheme {
                NavigationSetup(
                    chatViewModel = ChatViewModel(),
                    paddingValues = PaddingValues(0.dp)
                )
            }
        }
    }
}










