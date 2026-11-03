package com.vegspro.winxdressup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vegspro.winxdressup.data.Character
import com.vegspro.winxdressup.data.GameData
import com.vegspro.winxdressup.data.ItemCategory
import com.vegspro.winxdressup.data.OutfitItem
import com.vegspro.winxdressup.ui.theme.SoftWhite

@Composable
fun CharacterPreview(
    character: Character,
    outfit: Map<ItemCategory, String>,
    getItem: (String) -> OutfitItem?,
    modifier: Modifier = Modifier,
    large: Boolean = true
) {
    val size = if (large) 220.dp else 120.dp
    val headSize = if (large) 70.dp else 40.dp

    Box(
        modifier = modifier
            .size(size)
            .shadow(12.dp, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        character.themeColor.copy(alpha = 0.3f),
                        Color(0xFF1A0033).copy(alpha = 0.8f)
                    )
                )
            )
            .border(2.dp, character.themeColor.copy(alpha = 0.6f), RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Wings behind
            val wings = outfit[ItemCategory.WINGS]?.let { getItem(it) }
            if (wings != null) {
                Text(
                    text = wings.emoji,
                    fontSize = if (large) 48.sp else 28.sp,
                    modifier = Modifier.offset(y = if (large) 10.dp else 4.dp)
                )
            }

            // Head / Hair
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(headSize)
                    .clip(CircleShape)
                    .background(character.themeColor.copy(alpha = 0.9f))
                    .border(3.dp, SoftWhite.copy(alpha = 0.5f), CircleShape)
            ) {
                val hair = outfit[ItemCategory.HAIR]?.let { getItem(it) }
                Text(
                    text = hair?.emoji ?: character.emoji,
                    fontSize = if (large) 32.sp else 20.sp
                )
            }

            Spacer(modifier = Modifier.height(if (large) 8.dp else 4.dp))

            // Body / Top
            val top = outfit[ItemCategory.TOP]?.let { getItem(it) }
            Box(
                modifier = Modifier
                    .width(if (large) 90.dp else 50.dp)
                    .height(if (large) 50.dp else 28.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(top?.color ?: character.themeColor)
                    .border(1.dp, SoftWhite.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = top?.emoji ?: "👚", fontSize = if (large) 20.sp else 12.sp)
            }

            // Bottom
            val bottom = outfit[ItemCategory.BOTTOM]?.let { getItem(it) }
            Box(
                modifier = Modifier
                    .width(if (large) 80.dp else 44.dp)
                    .height(if (large) 40.dp else 22.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(bottom?.color ?: character.themeColor.copy(alpha = 0.7f))
                    .border(1.dp, SoftWhite.copy(alpha = 0.2f), RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = bottom?.emoji ?: "👗", fontSize = if (large) 16.sp else 10.sp)
            }

            // Shoes
            val shoes = outfit[ItemCategory.SHOES]?.let { getItem(it) }
            Row(
                horizontalArrangement = Arrangement.spacedBy(if (large) 12.dp else 6.dp),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(text = shoes?.emoji ?: "👠", fontSize = if (large) 18.sp else 12.sp)
                Text(text = shoes?.emoji ?: "👠", fontSize = if (large) 18.sp else 12.sp)
            }

            // Accessory
            val acc = outfit[ItemCategory.ACCESSORY]?.let { getItem(it) }
            if (acc != null && large) {
                Text(
                    text = acc.emoji,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Character name badge
        if (large) {
            Text(
                text = character.nameRu,
                style = MaterialTheme.typography.labelLarge,
                color = SoftWhite,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .background(character.themeColor.copy(alpha = 0.8f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun MiniOutfitCard(
    characterName: String,
    roomName: String,
    themeColor: Color,
    emoji: String,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(themeColor.copy(alpha = 0.4f), Color(0xFF2D1B4E))
                )
            )
            .border(1.dp, themeColor.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(themeColor.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 28.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = characterName,
                    color = SoftWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = roomName,
                    color = SoftWhite.copy(alpha = 0.7f),
                    fontSize = 13.sp
                )
            }
        }
    }
}
