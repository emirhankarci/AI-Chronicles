package com.example.ai_rpg_project.Screens.NameScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ai_rpg_project.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameSelecttScreen(
    navController: NavHostController,
    viewModel: NameSelectViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1B1D3C))
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TopNameAndIcon()

        Spacer(modifier = Modifier.height(80.dp))


        CustomTextField(
            name = viewModel.name,
            onNameChanged = viewModel::onNameChanged,
            onBeginJourneyClicked = { viewModel.onBeginJourneyClicked { route -> navController.navigate(route) } }
        )

        FooterText()
    }
}

@Composable
private fun FooterText() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 36.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose your name wisely, brave warrior. This name will echo through the halls of legend as you forge your path to glory.",
            color = Color.Gray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

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
    name: String,
    onNameChanged: (String) -> Unit,
    onBeginJourneyClicked: () -> Unit
) {
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
            .background(Color(0xFF1B1D3C))
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { onNameChanged(it) },
            placeholder = {
                Text(
                    text = "Enter your name", color = Color(0xFFAAAAAA), fontSize = 16.sp
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Icon", tint = Color(0xFFFFD700)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFF2E3264),
                focusedBorderColor = Color(0xFFFFD700),
                unfocusedBorderColor = Color(0xFF4E5180),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFFFFD700)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
    Button(
        onClick = onBeginJourneyClicked,
        enabled = name.isNotBlank(),
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFA500),
            contentColor = Color.Black
        )
    ) {
        Text(
            text = "BEGIN JOURNEY", fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNameSelectScreen() {
    val fakeNavController = rememberNavController()
    NameSelecttScreen(navController = fakeNavController)
}