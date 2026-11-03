package com.vegspro.winxdressup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vegspro.winxdressup.data.GameData
import com.vegspro.winxdressup.data.SavedOutfit
import com.vegspro.winxdressup.ui.theme.PinkPrimary
import com.vegspro.winxdressup.ui.theme.SoftWhite
import com.vegspro.winxdressup.viewmodel.DressUpViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    viewModel: DressUpViewModel,
    onBack: () -> Unit,
    onLoadOutfit: (SavedOutfit) -> Unit
) {
    val outfits by viewModel.savedOutfits.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Галерея нарядов 💖", color = SoftWhite) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = SoftWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2D1B4E)
                )
            )
        },
        containerColor = Color(0xFF1A0033)
    ) { padding ->
        if (outfits.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✨", fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Пока нет сохранённых нарядов",
                        color = SoftWhite,
                        fontSize = 18.sp
                    )
                    Text(
                        "Создай образ и нажми 💾",
                        color = SoftWhite.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(outfits, key = { it.id }) { outfit ->
                    val char = GameData.characters.find { it.id == outfit.characterId }
                    val themeColor = char?.themeColor ?: PinkPrimary
                    val emoji = char?.emoji ?: "🧚"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLoadOutfit(outfit) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2D1B4E))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(themeColor.copy(alpha = 0.35f), Color(0xFF2D1B4E))
                                    )
                                )
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(themeColor.copy(alpha = 0.7f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(emoji, fontSize = 28.sp)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    outfit.characterName,
                                    color = SoftWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                                Text(
                                    outfit.roomName,
                                    color = SoftWhite.copy(alpha = 0.7f),
                                    fontSize = 13.sp
                                )
                                Text(
                                    formatDate(outfit.timestamp),
                                    color = SoftWhite.copy(alpha = 0.5f),
                                    fontSize = 11.sp
                                )
                            }
                            IconButton(onClick = { viewModel.deleteOutfit(outfit.id) }) {
                                Icon(Icons.Default.Delete, "Удалить", tint = Color(0xFFFF8A80))
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatDate(ts: Long): String {
    if (ts == 0L) return ""
    return try {
        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(ts))
    } catch (_: Exception) {
        ""
    }
}
