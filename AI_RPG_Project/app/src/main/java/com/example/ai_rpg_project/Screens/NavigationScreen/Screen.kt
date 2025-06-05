package com.example.ai_rpg_project.Screens.NavigationScreen

// Navigation/Screen.kt (Yeni bir dosya oluştur)
sealed class Screen(val route: String) {
    object Welcome : Screen("WelcomeScreen")
    object NameSelect : Screen("NameSelecttScreen")
    object ThemeSelect : Screen("EpicRealmsUI/{name}") {
        fun createRoute(name: String) = "EpicRealmsUI/$name"
    }
    object CreateStory : Screen("CreateStoryScreen")
    object CharacterSelect : Screen("CharacterSelection/{name}/{setting}") {
        fun createRoute(name: String, setting: String) = "CharacterSelection/$name/$setting"
    }
    object SavedGames : Screen("SavedGamesScreen")

    // ChatPage için 2 farklı rotayı TEK bir rotada birleştiriyoruz.
    // Artık parametreye ihtiyacımız yok!
    object Chat : Screen("ChatPage")
}