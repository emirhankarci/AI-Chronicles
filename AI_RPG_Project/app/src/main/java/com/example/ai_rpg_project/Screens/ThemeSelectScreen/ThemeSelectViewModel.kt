package com.example.ai_rpg_project.Screens.EpicRealmsUI

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ai_rpg_project.R
import com.example.ai_rpg_project.data.AdventureTheme
import com.example.ai_rpg_project.data.ThemeCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EpicRealmsViewModel @Inject constructor() : ViewModel() {


    var selectedCard by mutableStateOf<String?>(null)
        private set


    var selectedCategory by mutableStateOf(ThemeCategory.ALL)
        private set


    val allThemes: List<AdventureTheme> = listOf(
        AdventureTheme(
            "Fantasy Kingdom",
            R.drawable.island,
            "A realm of castles, magic, and legendary creatures.",
            ThemeCategory.ADVENTURE
        ),
        AdventureTheme(
            "Dark Forest",
            R.drawable.forest,
            "A mysterious woodland filled with ancient secrets and lurking dangers.",
            ThemeCategory.ADVENTURE
        ),
        AdventureTheme(
            "Desert Empire",
            R.drawable.desertempire,
            "A vast desert land with lost cities, hidden treasures, and powerful dynasties.",
            ThemeCategory.HISTORY
        ),
        AdventureTheme(
            "Cyberpunk City",
            R.drawable.cyberpunktheme,
            "A neon-lit metropolis ruled by technology, corporations, and rebellion.",
            ThemeCategory.SPACE
        ),
        AdventureTheme(
            "Neon Rebellion",
            R.drawable.neon_rebellion,
            "A futuristic dystopia of high-tech chaos and underground resistance.",
            ThemeCategory.SPACE
        ),
        AdventureTheme(
            "Galactic Frontier",
            R.drawable.galactic_fugitive,
            "A sprawling sci-fi universe of space stations, distant planets, and interstellar intrigue.",
            ThemeCategory.SPACE
        ),
        AdventureTheme(
            "AI Uprising",
            R.drawable.ai_awakening,
            "A world where artificial intelligence and humanity struggle for dominance.",
            ThemeCategory.SPACE
        ),
        AdventureTheme(
            "Cursed Lands",
            R.drawable.cursed_bloodline,
            "A dark and gothic world filled with supernatural forces and forgotten bloodlines.",
            ThemeCategory.ADVENTURE
        ),
        AdventureTheme(
            "Forbidden Magic",
            R.drawable.the_last_necromancer,
            "A world where mystical arts are outlawed, yet power still lingers in the shadows.",
            ThemeCategory.ADVENTURE
        ),
        AdventureTheme(
            "The Void",
            R.drawable.voidwalker,
            "A realm where reality bends, nightmares manifest, and unseen forces lurk.",
            ThemeCategory.SPACE
        ),
        AdventureTheme(
            "Post-Apocalyptic Ruins",
            R.drawable.after_the_collapse,
            "A world left in ruins, where survival is the only rule.",
            ThemeCategory.ADVENTURE
        ),
        AdventureTheme(
            "Biotech Plague",
            R.drawable.the_virus_war,
            "A setting where bioengineering has reshaped humanity in ways beyond imagination.",
            ThemeCategory.SPACE
        ),
        AdventureTheme(
            "Mutant Wasteland",
            R.drawable.mutant_chronicles,
            "A radioactive world where strange mutations define the new order.",
            ThemeCategory.SPACE
        ),
        AdventureTheme(
            "Neo-Noir City",
            R.drawable.neo_noir_detective,
            "A rain-soaked urban landscape of crime, corruption, and deception.",
            ThemeCategory.ADVENTURE
        ),
        AdventureTheme(
            "Mind Network",
            R.drawable.mind_hacker,
            "A futuristic world where memories, thoughts, and identities can be altered.",
            ThemeCategory.SPACE
        ),
        AdventureTheme(
            "Underworld Syndicate",
            R.drawable.the_last_heist,
            "A crime-infested society ruled by gangs, heists, and betrayals.",
            ThemeCategory.ADVENTURE
        ),
        AdventureTheme(
            "Feudal Warzone",
            R.drawable.samurais_ghost,
            "An ancient land of honor, war, and supernatural legends.",
            ThemeCategory.HISTORY
        ),
        AdventureTheme(
            "Norse Realms",
            R.drawable.norse_revenant,
            "A world of Viking sagas, forgotten gods, and looming battles.",
            ThemeCategory.HISTORY
        ),
        AdventureTheme(
            "Ancient Mythos",
            R.drawable.egyptian_secrets,
            "A civilization of lost tombs, divine relics, and sacred mysteries.",
            ThemeCategory.HISTORY
        ),
        AdventureTheme(
            "Destiny's Gamble",
            R.drawable.the_chosen_or_not,
            "A world shaped by fate, prophecy, and unexpected twists.",
            ThemeCategory.ADVENTURE
        ),
        AdventureTheme(
            "Arcane Contracts",
            R.drawable.the_mages_bargain,
            "A land where magic is bound by deals, sacrifices, and hidden costs.",
            ThemeCategory.ADVENTURE
        ),
        AdventureTheme(
            "Shifting Dungeon",
            R.drawable.the_living_dungeon,
            "A labyrinth that constantly changes, challenging all who enter.",
            ThemeCategory.ADVENTURE
        ),
    )


    val filteredThemes: List<AdventureTheme>
        get() = allThemes.filter { theme ->
            selectedCategory == ThemeCategory.ALL || theme.category == selectedCategory
        }

    fun onThemeSelected(title: String) {
        selectedCard = title
    }

    fun onCategorySelected(category: ThemeCategory) {
        selectedCategory = category
        selectedCard = null
    }

    fun onBeginJourneyClicked(name: String, onNavigate: (String, String) -> Unit) {
        selectedCard?.let { setting ->
            onNavigate(name, setting)
        }
    }
}