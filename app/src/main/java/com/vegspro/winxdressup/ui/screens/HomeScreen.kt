package com.vegspro.winxdressup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vegspro.winxdressup.data.GameData
import com.vegspro.winxdressup.ui.theme.PinkPrimary
import com.vegspro.winxdressup.ui.theme.SoftWhite

@Composable
fun HomeScreen(
    onStartDressUp: () -> Unit,
    onOpenGallery: () -> Unit,
    onSelectRoom: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        androidx.compose.ui.graphics.Color(0xFF2D1B4E),
                        androidx.compose.ui.graphics.Color(0xFF1A0033)
                    )
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "✨ Winx Dress Up ✨",
            style = MaterialTheme.typography.displayLarge,
            color = SoftWhite,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Одевалки фей Винкс",
            style = MaterialTheme.typography.titleMedium,
            color = PinkPrimary,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Main buttons
        HomeCard(
            title = "Начать одеваться",
            subtitle = "Выбери фею и создай наряд",
            emoji = "🧚‍♀️",
            onClick = onStartDressUp
        )
        Spacer(modifier = Modifier.height(16.dp))
        HomeCard(
            title = "Волшебные комнаты",
            subtitle = "Алфея, сад, сцена и другие",
            emoji = "🏰",
            onClick = onSelectRoom
        )
        Spacer(modifier = Modifier.height(16.dp))
        HomeCard(
            title = "Галерея нарядов",
            subtitle = "Твои сохранённые образы",
            emoji = "💖",
            onClick = onOpenGallery
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Все феи Клуба Винкс ждут тебя!",
            color = SoftWhite.copy(alpha = 0.6f),
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun HomeCard(
    title: String,
    subtitle: String,
    emoji: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = androidx.compose.ui.graphics.Color(0xFF3D2A5C)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = emoji, fontSize = 40.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    color = SoftWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = subtitle,
                    color = SoftWhite.copy(alpha = 0.7f),
                    fontSize = 13.sp
                )
            }
        }
    }
}
