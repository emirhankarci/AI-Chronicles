import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import com.example.ai_rpg_project.R


@Composable
fun CharacterSelectionScreen(
    name: String,
    setting: String,
    onCharacterSelected: (String, String, String, CharacterStats) -> Unit
) {
    val characters = listOf(
        "Thief" to CharacterDetails(
            iconRes = R.drawable.thief,
            backgroundColor = Color(0xFF1C1C3C),
            stats = CharacterStats(strength = 70, defense = 50, speed = 90)
        ),
        "King" to CharacterDetails(
            iconRes = R.drawable.king,
            backgroundColor = Color(0xFFFFD700),
            stats = CharacterStats(strength = 90, defense = 80, speed = 60)
        ),
        "Wizard" to CharacterDetails(
            iconRes = R.drawable.wizard,
            backgroundColor = Color(0xFF4169E1),
            stats = CharacterStats(strength = 60, defense = 50, speed = 80)
        ),
        "Warrior" to CharacterDetails(
            iconRes = R.drawable.warrior,
            backgroundColor = Color(0xFF8B0000),
            stats = CharacterStats(strength = 85, defense = 70, speed = 70)
        ),
        "Knight" to CharacterDetails(
            iconRes = R.drawable.knight,
            backgroundColor = Color.Gray,
            stats = CharacterStats(strength = 80, defense = 90, speed = 50)
        ),
        "Prince" to CharacterDetails(
            iconRes = R.drawable.prince,
            backgroundColor = Color.Magenta,
            stats = CharacterStats(strength = 75, defense = 65, speed = 85)
        ),
        "Peasant" to CharacterDetails(
            iconRes = R.drawable.peasant,
            backgroundColor = Color.Blue,
            stats = CharacterStats(strength = 60, defense = 40, speed = 50)
        ),
        "Vampire" to CharacterDetails(
            iconRes = R.drawable.vampire,
            backgroundColor = Color(0xFF8B0000),
            stats = CharacterStats(strength = 95, defense = 60, speed = 90)
        )
    )

    var selectedCharacter by remember { mutableStateOf<String?>(null) }
    var selectedCharacterStats by remember { mutableStateOf<CharacterStats?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C0F2E)) // Dark background
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose Your Character",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(characters) { (charName, details) ->
                CharacterCard(
                    char_name = charName,
                    iconRes = details.iconRes,
                    backgroundColor = details.backgroundColor,
                    isSelected = selectedCharacter == charName,
                    characterStats = details.stats,
                    onClick = {
                        selectedCharacter = charName
                        selectedCharacterStats = details.stats
                    }
                )
            }
        }


        Button(
            onClick = {
                selectedCharacter?.let { charName ->
                    selectedCharacterStats?.let { stats ->
                        onCharacterSelected(name, setting, charName, stats)
                    }
                }
            },
            enabled = selectedCharacter != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Select Character")
        }

    }
}


@Composable
fun CharacterCard(
    char_name: String,
    iconRes: Int,
    backgroundColor: Color,
    isSelected: Boolean,
    characterStats: CharacterStats?,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable {
                    onClick() // Select character
                    expanded = !expanded // Toggle dropdown
                },
            shape = RoundedCornerShape(16.dp),
            border = if (isSelected) BorderStroke(3.dp, Color.Blue) else null,
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF241D77))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(backgroundColor)
                    ) {
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = char_name,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = char_name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }

        // Dropdown Appears Directly Under the Selected Card
        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = char_name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    characterStats?.let {
                        CharacterStat("Strength", it.strength)
                        CharacterStat("Defense", it.defense)
                        CharacterStat("Speed", it.speed)
                    }
                }
            }
        }
    }
}





@Composable
fun CharacterStat(label: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        LinearProgressIndicator(
            progress = value / 100f,
            modifier = Modifier
                .width(25.dp)
                .height(8.dp),
            color = Color.Red
        )
        Text(
            text = value.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


data class CharacterStats(val strength: Int, val defense: Int, val speed: Int)

data class CharacterDetails(val iconRes: Int, val backgroundColor: Color, val stats: CharacterStats)




