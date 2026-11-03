package com.vegspro.winxdressup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vegspro.winxdressup.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class DressUpViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = OutfitRepository(application)

    val savedOutfits = repository.savedOutfits
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val selectedCharacter: StateFlow<Character?> = _selectedCharacter.asStateFlow()

    private val _selectedRoom = MutableStateFlow(GameData.rooms.first())
    val selectedRoom: StateFlow<Room> = _selectedRoom.asStateFlow()

    private val _currentOutfit = MutableStateFlow<Map<ItemCategory, String>>(emptyMap())
    val currentOutfit: StateFlow<Map<ItemCategory, String>> = _currentOutfit.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ItemCategory.HAIR)
    val selectedCategory: StateFlow<ItemCategory> = _selectedCategory.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun selectCharacter(character: Character) {
        _selectedCharacter.value = character
        _currentOutfit.value = GameData.getDefaultOutfit(character.id)
    }

    fun selectRoom(room: Room) {
        _selectedRoom.value = room
    }

    fun selectCategory(category: ItemCategory) {
        _selectedCategory.value = category
    }

    fun selectItem(item: OutfitItem) {
        val updated = _currentOutfit.value.toMutableMap()
        updated[item.category] = item.id
        _currentOutfit.value = updated
    }

    fun clearMessage() {
        _message.value = null
    }

    fun saveCurrentOutfit() {
        val char = _selectedCharacter.value ?: return
        val room = _selectedRoom.value
        val outfit = SavedOutfit(
            id = UUID.randomUUID().toString(),
            characterId = char.id,
            characterName = char.nameRu,
            roomId = room.id,
            roomName = room.nameRu,
            items = _currentOutfit.value
        )
        viewModelScope.launch {
            repository.saveOutfit(outfit)
            _message.value = "Наряд сохранён! ✨"
        }
    }

    fun deleteOutfit(id: String) {
        viewModelScope.launch {
            repository.deleteOutfit(id)
            _message.value = "Удалено"
        }
    }

    fun loadOutfit(saved: SavedOutfit) {
        val char = GameData.characters.find { it.id == saved.characterId }
        val room = GameData.rooms.find { it.id == saved.roomId }
        if (char != null) {
            _selectedCharacter.value = char
            _currentOutfit.value = saved.items
            if (room != null) _selectedRoom.value = room
        }
    }

    fun getItemById(id: String): OutfitItem? = GameData.allItems.find { it.id == id }

    fun getItemsForCategory(category: ItemCategory): List<OutfitItem> =
        GameData.allItems.filter { it.category == category }
}
