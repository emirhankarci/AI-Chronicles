package com.example.ai_rpg_project.Screens.EpicRealmsUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ai_rpg_project.data.ThemeCategory


@Composable
fun ThemeFilterButton(
    text: String, isSelected: Boolean, onClick: () -> Unit
) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(8.dp))
        .clickable { onClick() }
        .background(
            if (isSelected) Color(0xFF4169E1)
            else Color(0xFF2A2A4A)
        )
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = text, color = if (isSelected) Color.White else Color.LightGray, fontSize = 16.sp
        )
    }
}

@Composable
fun ThemeSelection(
    name: String,
    onNavigate: (String, String) -> Unit,
    onNavigateToCreate: () -> Unit,
    viewModel: EpicRealmsViewModel = hiltViewModel()
) {

    val selectedCard = viewModel.selectedCard
    val selectedCategory = viewModel.selectedCategory
    val filteredThemes = viewModel.filteredThemes

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF1C1C3C))
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
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
                text = "Choose your adventure theme", color = Color.LightGray, fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Theme Filter Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ThemeFilterButton(
                    text = "All", isSelected = selectedCategory == ThemeCategory.ALL
                ) { viewModel.onCategorySelected(ThemeCategory.ALL) }

                ThemeFilterButton(
                    text = "Adventure", isSelected = selectedCategory == ThemeCategory.ADVENTURE
                ) { viewModel.onCategorySelected(ThemeCategory.ADVENTURE) }

                ThemeFilterButton(
                    text = "Space", isSelected = selectedCategory == ThemeCategory.SPACE
                ) { viewModel.onCategorySelected(ThemeCategory.SPACE) }

                ThemeFilterButton(
                    text = "History", isSelected = selectedCategory == ThemeCategory.HISTORY
                ) { viewModel.onCategorySelected(ThemeCategory.HISTORY) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.85f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredThemes) { theme ->
                    AdventureCard(
                        title = theme.title,
                        description = theme.description,
                        imageRes = theme.imageRes,
                        isSelected = selectedCard == theme.title,
                        onClick = { viewModel.onThemeSelected(theme.title) })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.onBeginJourneyClicked(name, onNavigate) },
                enabled = selectedCard != null,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500), contentColor = Color.Black
                )
            ) {
                Text(
                    text = "BEGIN JOURNEY", fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            }
        }


        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF1C1C3C)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
                Text(
                    text = "Home", color = Color.White, fontSize = 12.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigateToCreate() }) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Create",
                    tint = Color.White
                )
                Text(
                    text = "Create", color = Color.White, fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun AdventureCard(
    title: String, description: String, imageRes: Int, isSelected: Boolean, onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() }
            .background(if (isSelected) Color(0xFFAAAAFF) else Color.Transparent)) {
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
    ThemeSelection(name = "Hero", onNavigate = { _, _ -> }, onNavigateToCreate = {})
}