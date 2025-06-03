package com.example.ai_rpg_project.Screens.CreateStoryScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ai_rpg_project.R

@Composable
fun CreateStoryScreen(
    navController: NavController,
    viewModel: CreateStoryScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()


    val story = viewModel.story
    val mainCharacter = viewModel.mainCharacter
    val sideCharacters = viewModel.sideCharacters
    val specialItem = viewModel.specialItem
    val CSstrength = viewModel.CSstrength
    val CSdefense = viewModel.CSdefense
    val CSspeed = viewModel.CSspeed


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF181433))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Describe your story", color = Color.White, fontSize = 16.sp)
            OutlinedTextField(
                value = story,
                onValueChange = { viewModel.onStoryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = { Text("Enter your story...") },
                colors = textFieldColors()
            )

            Text("Main Character", color = Color.White, fontSize = 16.sp)
            OutlinedTextField(
                value = mainCharacter,
                onValueChange = { viewModel.onMainCharacterChange(it) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Text("Side Characters", color = Color.White, fontSize = 16.sp)
            OutlinedTextField(
                value = sideCharacters,
                onValueChange = { viewModel.onSideCharactersChange(it) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Text("Special Item", color = Color.White, fontSize = 16.sp)
            OutlinedTextField(
                value = specialItem,
                onValueChange = { viewModel.onSpecialItemChange(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Want an Excalibur?") },
                colors = textFieldColors()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Strength",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.strengthsword),
                            contentDescription = "Sword icon",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )
                    }

                    OutlinedTextField(
                        value = CSstrength,
                        onValueChange = { viewModel.onStrengthChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors()
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Defense",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.defenseshield),
                            contentDescription = "Defense icon",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )
                    }

                    OutlinedTextField(
                        value = CSdefense,
                        onValueChange = { viewModel.onDefenseChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors()
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Speed",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.speedflash),
                            contentDescription = "Speed icon",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified
                        )
                    }
                    OutlinedTextField(
                        value = CSspeed,
                        onValueChange = { viewModel.onSpeedChange(it) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors()
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    viewModel.onSubmitClicked { route -> navController.navigate(route) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Submit", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = Color(0xFF2D2956),
    unfocusedContainerColor = Color(0xFF2D2956),
    cursorColor = Color.White,
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedPlaceholderColor = Color.LightGray,
    unfocusedPlaceholderColor = Color.LightGray
)