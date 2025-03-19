package com.example.ai_rpg_project

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameSelecttScreen(
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1D3C))
            .padding(top = 40.dp, start = 16.dp, end = 16.dp), // Add padding for layout consistency
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Space out elements
    ) {

        TopNameAndIcon()


        Spacer(modifier = Modifier.height(80.dp))

        CustomTextField(navController)


        FooterText()
    }
}

@Composable
private fun FooterText() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 36.dp), // Add padding for spacing
        verticalArrangement = Arrangement.Bottom, // Push content to the bottom
        horizontalAlignment = Alignment.CenterHorizontally // Center text horizontally
    ) {
        Text(
            text = "Choose your name wisely, brave warrior. This name will echo through the halls of legend as you forge your path to glory.",
            color = Color.Gray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp) // Padding for better readability
        )

        Spacer(modifier = Modifier.height(32.dp)) // Small space between texts

        Text(
            text = "Your name cannot be changed once your journey begins",
            color = Color.Gray,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}



@Composable
private fun TopNameAndIcon() {
    Image(
        painter = painterResource(id = R.drawable.skulllll),
        contentDescription = null,
        Modifier
            .size(150.dp)
            .padding(bottom = 5.dp)
    )
    Text(
        text = "AI CHRONICLES",
        color = Color(0xFFFFD700),
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    Text(
        text = "CHOOSE YOUR NAME",
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .background(Color(0xFF1B1D3C)) // Background color for the screen
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = {
                Text(
                    text = "Enter your name",
                    color = Color(0xFFAAAAAA),
                    fontSize = 16.sp
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Star, // Replace with your custom icon
                    contentDescription = "Icon",
                    tint = Color(0xFFFFD700)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFF2E3264),
                focusedBorderColor = Color(0xFFFFD700),
                unfocusedBorderColor = Color(0xFF4E5180),
                focusedTextColor = Color.White, // Use this for text color
                unfocusedTextColor = Color.White, // Keeps text white even when unfocused
                cursorColor = Color(0xFFFFD700)
            )
            ,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

    }
    Button(
        onClick = { navController.navigate("EpicRealmsUI/$name") }, // ✅ Pass name to setting selection screen
        enabled = name.isNotBlank(),
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFA500), // Plain orange color
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


// Extension function to use Brush in button colors
private fun Brush.toColor(): Color {
    return Color.Transparent // Placeholder as gradients cannot directly be used in Buttons
}

@Preview(showBackground = true)
@Composable
fun PreviewNameSelectScreen() {
    // Creating a fake NavController for preview
    val fakeNavController = rememberNavController()

    NameSelecttScreen(navController = fakeNavController)
}
