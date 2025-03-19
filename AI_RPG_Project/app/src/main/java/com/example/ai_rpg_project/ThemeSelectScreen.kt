import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai_rpg_project.R



@Composable
fun EpicRealmsUI(name: String, onNavigate: (String, String) -> Unit) {
    var selectedCard by remember { mutableStateOf<String?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C3C))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AI CHRONICLES",
            color = Color(0xFFFFD700),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Choose your adventure theme",
            color = Color.LightGray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        val themes = listOf(
            Triple("Fantasy Kingdom", R.drawable.island, "A realm of castles, magic, and legendary creatures."),
            Triple("Dark Forest", R.drawable.forest, "A mysterious woodland filled with ancient secrets and lurking dangers."),
            Triple("Desert Empire", R.drawable.desertempire, "A vast desert land with lost cities, hidden treasures, and powerful dynasties."),
            Triple("Cyberpunk City", R.drawable.cyberpunktheme, "A neon-lit metropolis ruled by technology, corporations, and rebellion."),
            Triple("Neon Rebellion", R.drawable.neon_rebellion, "A futuristic dystopia of high-tech chaos and underground resistance."),
            Triple("Galactic Frontier", R.drawable.galactic_fugitive, "A sprawling sci-fi universe of space stations, distant planets, and interstellar intrigue."),
            Triple("AI Uprising", R.drawable.ai_awakening, "A world where artificial intelligence and humanity struggle for dominance."),
            Triple("Cursed Lands", R.drawable.cursed_bloodline, "A dark and gothic world filled with supernatural forces and forgotten bloodlines."),
            Triple("Forbidden Magic", R.drawable.the_last_necromancer, "A world where mystical arts are outlawed, yet power still lingers in the shadows."),
            Triple("The Void", R.drawable.voidwalker, "A realm where reality bends, nightmares manifest, and unseen forces lurk."),
            Triple("Post-Apocalyptic Ruins", R.drawable.after_the_collapse, "A world left in ruins, where survival is the only rule."),
            Triple("Biotech Plague", R.drawable.the_virus_war, "A setting where bioengineering has reshaped humanity in ways beyond imagination."),
            Triple("Mutant Wasteland", R.drawable.mutant_chronicles, "A radioactive world where strange mutations define the new order."),
            Triple("Neo-Noir City", R.drawable.neo_noir_detective, "A rain-soaked urban landscape of crime, corruption, and deception."),
            Triple("Mind Network", R.drawable.mind_hacker, "A futuristic world where memories, thoughts, and identities can be altered."),
            Triple("Underworld Syndicate", R.drawable.the_last_heist, "A crime-infested society ruled by gangs, heists, and betrayals."),
            Triple("Feudal Warzone", R.drawable.samurais_ghost, "An ancient land of honor, war, and supernatural legends."),
            Triple("Norse Realms", R.drawable.norse_revenant, "A world of Viking sagas, forgotten gods, and looming battles."),
            Triple("Ancient Mythos", R.drawable.egyptian_secrets, "A civilization of lost tombs, divine relics, and sacred mysteries."),
            Triple("Destiny’s Gamble", R.drawable.the_chosen_or_not, "A world shaped by fate, prophecy, and unexpected twists."),
            Triple("Arcane Contracts", R.drawable.the_mages_bargain, "A land where magic is bound by deals, sacrifices, and hidden costs."),
            Triple("Shifting Dungeon", R.drawable.the_living_dungeon, "A labyrinth that constantly changes, challenging all who enter."),
            )

        LazyColumn(
            modifier = Modifier.fillMaxHeight(0.85f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(themes) { (title, imageRes, description) ->
                AdventureCard(
                    title = title,
                    description = description,
                    imageRes = imageRes,
                    isSelected = selectedCard == title,
                    onClick = { selectedCard = title }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { selectedCard?.let { setting -> onNavigate(name, setting) } },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "BEGIN JOURNEY",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AdventureCard(
    title: String,
    description: String,
    imageRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() }
            .background(if (isSelected) Color(0xFFAAAAFF) else Color.Transparent)
    ) {
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEpicRealmsUI() {
    EpicRealmsUI(name = "Hero") { _, _ -> }
}
