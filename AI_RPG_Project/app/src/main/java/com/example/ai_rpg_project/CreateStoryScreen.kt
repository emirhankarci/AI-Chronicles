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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai_rpg_project.R
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource

import android.net.Uri



@Composable
fun CreateStoryScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    // State variables for inputs
    var story by remember { mutableStateOf("") }
    var mainCharacter by remember { mutableStateOf("") }
    var sideCharacters by remember { mutableStateOf("") }
    var specialItem by remember { mutableStateOf("") }
    var CSstrength by remember { mutableStateOf("") }
    var CSdefense by remember { mutableStateOf("") }
    var CSspeed by remember { mutableStateOf("") }


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
                onValueChange = { story = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = { Text("Enter your story...") },
                colors = textFieldColors()
            )

            Text("Main Character", color = Color.White, fontSize = 16.sp)
            OutlinedTextField(
                value = mainCharacter,
                onValueChange = { mainCharacter = it },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Text("Side Characters", color = Color.White, fontSize = 16.sp)
            OutlinedTextField(
                value = sideCharacters,
                onValueChange = { sideCharacters = it },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
            )

            Text("Special Item", color = Color.White, fontSize = 16.sp)
            OutlinedTextField(
                value = specialItem,
                onValueChange = { specialItem = it },
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
                        .padding(end = 4.dp) // optional spacing between columns
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically // Aligning text and icon vertically
                    ) {
                        Text(
                            text = "Strength",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp) // Adding space between text and icon
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.strengthsword),
                            contentDescription = "Sword icon", // or null if it's decorative
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified// Reduce the size of the icon
                        )
                    }

                    OutlinedTextField(
                        value = CSstrength,
                        onValueChange = { CSstrength = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors()
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Row (
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically // Aligning text and icon vertically
                    ){
                        Text(
                            "Defense",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.defenseshield),
                            contentDescription = "Defense icon", // or null if it's decorative
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified// Reduce the size of the icon
                        )
                    }

                    OutlinedTextField(
                        value = CSdefense,
                        onValueChange = { CSdefense = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors()
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                ) {
                    Row (
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically // Aligning text and icon vertically
                    ){
                        Text(
                            "Speed",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.speedflash),
                            contentDescription = "Speed icon", // or null if it's decorative
                            modifier = Modifier.size(20.dp),
                            tint = Color.Unspecified// Reduce the size of the icon
                        )
                    }
                    OutlinedTextField(
                        value = CSspeed,
                        onValueChange = { CSspeed = it },
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
                    val encodedStory = Uri.encode(story)
                    val encodedMain = Uri.encode(mainCharacter)
                    val encodedSide = Uri.encode(sideCharacters)
                    val encodedItem = Uri.encode(specialItem)
                    val encodedCSStrength = Uri.encode(CSstrength)
                    val encodedCSDefense = Uri.encode(CSdefense)
                    val encodedCSSpeed = Uri.encode(CSspeed)

                    navController.navigate("ChatPageStory/$encodedStory/$encodedMain/$encodedSide/$encodedItem/$encodedCSStrength/$encodedCSDefense/$encodedCSSpeed")
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
