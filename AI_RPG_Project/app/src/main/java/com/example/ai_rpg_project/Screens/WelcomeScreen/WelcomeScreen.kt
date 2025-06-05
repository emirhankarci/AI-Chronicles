package com.example.ai_rpg_project.Screens.WelcomeScreen

import androidx.compose.foundation.Image
import androidx.compose.runtime.remember
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ai_rpg_project.R
import com.example.ai_rpg_project.Screens.ChatPageScreen.ChatViewModel

@Composable
fun WelcomeScreen(
    navController: NavHostController,
    chatViewModel: ChatViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2D0743), Color.Black)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(0.dp))

            WelcomeText()

            Spacer(modifier = Modifier.height(0.dp))

            GameMenu(navController, chatViewModel)

            Text(
                text = "v1.0.0",
                color = Color.LightGray,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
@Composable
private fun WelcomeText() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.gamepad),
            contentDescription = null,
            Modifier.size(115.dp)
        )
        Text(
            text = "Welcome",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 3f
                )
            )
        )

        Text(
            text = "Begin Your Adventure", fontSize = 16.sp, color = Color.White, style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(1f, 1f),
                    blurRadius = 2f
                )
            )
        )

    }
}
@Composable
private fun GameMenu(
    navController: NavHostController,
    chatViewModel: ChatViewModel
) {
    val purpleGradient = listOf(Color(0xFF8E2DE2), Color(0xFF4A00E0))
    val grayGradient = listOf(Color(0xFF2C2C2C), Color(0xFF8E8E8E))
    val disabledGradient = listOf(Color(0xFF1A1A1A), Color(0xFF3A3A3A))


    val isSessionLoaded by chatViewModel.isSessionLoaded.observeAsState(false)


    val hasContinuableGame by remember {
        derivedStateOf {
            isSessionLoaded && chatViewModel.hasContinuableGame()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.7f)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GameMenuButton(
                text = "Start New Game",
                icon = Icons.Default.PlayArrow,
                gradientColors = purpleGradient,
                isEnabled = true,
                onClick = {
                    // Oyunu sıfırla ve yeni oyun başlat
                    chatViewModel.resetGame()
                    navController.navigate("NameSelecttScreen")
                }
            )


            GameMenuButton(
                text = "Continue",
                icon = Icons.Default.PlayArrow,
                gradientColors = if (hasContinuableGame) grayGradient else disabledGradient,
                isEnabled = hasContinuableGame,
                onClick = {
                    if (hasContinuableGame) {

                        navController.navigate("ChatPage")
                    }
                }
            )


            GameMenuButton(
                text = "Load Game",
                icon = Icons.Default.Info,
                gradientColors = grayGradient,
                isEnabled = true,
                onClick = {
                    navController.navigate("SavedGamesScreen")
                }
            )


            GameMenuButton(
                text = "Settings",
                icon = Icons.Default.Settings,
                gradientColors = grayGradient,
                isEnabled = true,
                onClick = {
                    navController.navigate("NameSelecttScreen")
                }
            )
        }
    }
}

@Composable
fun GameMenuButton(
    text: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val buttonAlpha = if (isEnabled) 1f else 0.5f

    Button(
        onClick = { if (isEnabled) onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(),
        enabled = isEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(gradientColors),
                    shape = RoundedCornerShape(12.dp),
                    alpha = buttonAlpha
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = buttonAlpha)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text,
                    color = Color.White.copy(alpha = buttonAlpha),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}