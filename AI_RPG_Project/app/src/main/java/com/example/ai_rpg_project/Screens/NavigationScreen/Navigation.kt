package com.example.ai_rpg_project.Screens.NavigationScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ai_rpg_project.Screens.CharacterSelectionScreen.CharacterSelectionScreen
import com.example.ai_rpg_project.Screens.ChatPageScreen.ChatPage
import com.example.ai_rpg_project.Screens.ChatPageScreen.ChatViewModel
import com.example.ai_rpg_project.Screens.CreateStoryScreen.CreateStoryScreen
import com.example.ai_rpg_project.Screens.EpicRealmsUI.ThemeSelection
import com.example.ai_rpg_project.Screens.NameScreen.NameSelecttScreen
import com.example.ai_rpg_project.Screens.SavedGamesScreen.SavedGamesScreen
import com.example.ai_rpg_project.Screens.WelcomeScreen.WelcomeScreen

@Composable
fun NavigationSetup(
    chatViewModel: ChatViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val navController = rememberNavController()


    val isSessionLoaded by chatViewModel.isSessionLoaded.observeAsState(false)

    NavHost(navController = navController, startDestination = "WelcomeScreen") {
        composable("WelcomeScreen") {
            WelcomeScreen(
                navController = navController,
                chatViewModel = chatViewModel
            )
        }

        composable("NameSelecttScreen") {
            NameSelecttScreen(navController)
        }

        composable(
            route = "EpicRealmsUI/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            ThemeSelection(
                name = name,
                onNavigate = { selectedName, selectedSetting ->
                    navController.navigate("CharacterSelection/$selectedName/$selectedSetting")
                },
                onNavigateToCreate = {
                    navController.navigate("CreateStoryScreen")
                }
            )
        }

        composable("CreateStoryScreen") {
            CreateStoryScreen(navController = navController)
        }

        composable(
            route = "CharacterSelection/{name}/{setting}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("setting") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val setting = backStackEntry.arguments?.getString("setting") ?: ""

            CharacterSelectionScreen(
                name = name,
                setting = setting,
                onCharacterSelected = { selectedName, selectedSetting, selectedChar, stats ->

                    chatViewModel.startNewGame(
                        name = selectedName,
                        setting = selectedSetting,
                        charName = selectedChar,
                        strength = stats.strength,
                        defense = stats.defense,
                        speed = stats.speed
                    )

                    navController.navigate("ChatPage") {
                        popUpTo("WelcomeScreen")
                    }
                }
            )
        }

        composable("SavedGamesScreen") {
            SavedGamesScreen(navController, chatViewModel)
        }

        composable("ChatPage") {
            ChatPage(
                viewModel = chatViewModel,
                onNavigateToMainMenu = {

                    chatViewModel.onReturnToMainMenu()
                    navController.navigate("WelcomeScreen") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}