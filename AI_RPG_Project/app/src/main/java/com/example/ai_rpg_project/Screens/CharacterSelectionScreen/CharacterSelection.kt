package com.example.ai_rpg_project.Screens.CharacterSelectionScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ai_rpg_project.data.CharacterStats


@Composable
fun CharacterSelectionScreen(
    name: String,
    setting: String,
    onCharacterSelected: (String, String, String, CharacterStats) -> Unit,
    viewModel: CharacterSelectionViewModel = hiltViewModel()
) {

    val selectedCharacter = viewModel.selectedCharacter
    val characters = viewModel.characters
    val expandedStates = viewModel.expandedStates

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C0F2E))
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
            columns = GridCells.Fixed(2), modifier = Modifier
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

                    expanded = expandedStates[charName] == true,
                    onClick = {

                        viewModel.onCharacterCardClicked(charName, details.stats)
                    })
            }
        }

        Button(
            onClick = {
                viewModel.onSelectCharacterClicked(name, setting, onCharacterSelected)
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
    expanded: Boolean,
    onClick: () -> Unit
) {
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
                .clickable { onClick() },
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


        AnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.Start
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
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
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