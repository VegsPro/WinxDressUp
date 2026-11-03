package com.vegspro.winxdressup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vegspro.winxdressup.data.ItemCategory
import com.vegspro.winxdressup.data.OutfitItem
import com.vegspro.winxdressup.ui.components.CharacterPreview
import com.vegspro.winxdressup.ui.theme.PinkPrimary
import com.vegspro.winxdressup.ui.theme.SoftWhite
import com.vegspro.winxdressup.viewmodel.DressUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DressUpScreen(
    viewModel: DressUpViewModel,
    onBack: () -> Unit
) {
    val character by viewModel.selectedCharacter.collectAsState()
    val room by viewModel.selectedRoom.collectAsState()
    val outfit by viewModel.currentOutfit.collectAsState()
    val category by viewModel.selectedCategory.collectAsState()
    val message by viewModel.message.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    if (character == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Выбери фею сначала", color = SoftWhite)
        }
        return
    }

    val items = viewModel.getItemsForCategory(category)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(character!!.nameRu, color = SoftWhite, fontWeight = FontWeight.Bold)
                        Text(room.nameRu, color = SoftWhite.copy(alpha = 0.7f), fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = SoftWhite)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.saveCurrentOutfit() }) {
                        Icon(Icons.Default.Save, "Сохранить", tint = PinkPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2D1B4E)
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFF1A0033)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Preview area with room background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(room.backgroundColor.copy(alpha = 0.7f), Color(0xFF1A0033))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = room.emoji,
                    fontSize = 80.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .graphicsLayer { this.alpha = 0.3f }
                )
                CharacterPreview(
                    character = character!!,
                    outfit = outfit,
                    getItem = { viewModel.getItemById(it) },
                    large = true
                )
            }

            // Category tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ItemCategory.entries.forEach { cat ->
                    val selected = cat == category
                    val label = when (cat) {
                        ItemCategory.HAIR -> "Причёска"
                        ItemCategory.TOP -> "Верх"
                        ItemCategory.BOTTOM -> "Низ"
                        ItemCategory.SHOES -> "Обувь"
                        ItemCategory.WINGS -> "Крылья"
                        ItemCategory.ACCESSORY -> "Аксессуар"
                        ItemCategory.MAKEUP -> "Макияж"
                    }
                    FilterChip(
                        selected = selected,
                        onClick = { viewModel.selectCategory(cat) },
                        label = { Text(label, fontSize = 13.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PinkPrimary,
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFF3D2A5C),
                            labelColor = SoftWhite
                        )
                    )
                }
            }

            // Items grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    ItemCard(
                        item = item,
                        isSelected = outfit[item.category] == item.id,
                        onClick = { viewModel.selectItem(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemCard(
    item: OutfitItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) item.color.copy(alpha = 0.5f)
                else Color(0xFF2D1B4E)
            )
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) PinkPrimary else SoftWhite.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = item.emoji, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.name,
                color = SoftWhite,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                maxLines = 2
            )
        }
    }
}
