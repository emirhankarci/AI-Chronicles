package com.example.ai_rpg_project.Screens.NavigationScreen


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
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
    NavHost(navController = navController, startDestination = "WelcomeScreen") {
        composable("WelcomeScreen") {
            WelcomeScreen(navController = navController)
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
                    val strength = stats.strength
                    val defense = stats.defense
                    val speed = stats.speed
                    navController.navigate("ChatPage/$selectedName/$selectedSetting/$selectedChar/$strength/$defense/$speed")
                }
            )
        }

        composable("SavedGamesScreen") {
            SavedGamesScreen(navController, chatViewModel)
        }

        composable(
            route = "ChatPage/{name}/{setting}/{char_name}/{strength}/{defense}/{speed}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("setting") { type = NavType.StringType },
                navArgument("char_name") { type = NavType.StringType },
                navArgument("strength") { type = NavType.IntType },
                navArgument("defense") { type = NavType.IntType },
                navArgument("speed") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val setting = backStackEntry.arguments?.getString("setting") ?: ""
            val charName = backStackEntry.arguments?.getString("char_name") ?: ""
            val strength = backStackEntry.arguments?.getInt("strength") ?: 0
            val defense = backStackEntry.arguments?.getInt("defense") ?: 0
            val speed = backStackEntry.arguments?.getInt("speed") ?: 0


            ChatPage(
                viewModel = chatViewModel,
                name = name,
                setting = setting,
                charName = charName,
                strength = strength,
                defense = defense,
                speed = speed,
                specialItem = ""
            )
        }
        composable(
            route = "ChatPageStory/{story}/{mainCharacter}/{sideCharacters}/{specialItem}/{CSstrength}/{CSdefense}/{CSspeed}",
            arguments = listOf(
                navArgument("story") { type = NavType.StringType },
                navArgument("mainCharacter") { type = NavType.StringType },
                navArgument("sideCharacters") { type = NavType.StringType },
                navArgument("specialItem") { type = NavType.StringType },
                navArgument("CSstrength") { type = NavType.StringType },
                navArgument("CSdefense") { type = NavType.StringType },
                navArgument("CSspeed") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val story = backStackEntry.arguments?.getString("story") ?: ""
            val mainCharacter = backStackEntry.arguments?.getString("mainCharacter") ?: ""
            val sideCharacters = backStackEntry.arguments?.getString("sideCharacters") ?: ""
            val specialItem = backStackEntry.arguments?.getString("specialItem") ?: ""


            val strengthStr = backStackEntry.arguments?.getString("CSstrength") ?: "0"
            val defenseStr = backStackEntry.arguments?.getString("CSdefense") ?: "0"
            val speedStr = backStackEntry.arguments?.getString("CSspeed") ?: "0"


            val CSstrength = strengthStr.toIntOrNull() ?: 0
            val CSdefense = defenseStr.toIntOrNull() ?: 0
            val CSspeed = speedStr.toIntOrNull() ?: 0

            ChatPage(
                viewModel = chatViewModel,
                name = mainCharacter,
                setting = story,
                charName = sideCharacters,
                strength = CSstrength,
                defense = CSdefense,
                speed = CSspeed,
                specialItem = specialItem
            )
        }

    }
}