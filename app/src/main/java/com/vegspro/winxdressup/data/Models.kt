package com.vegspro.winxdressup.data

import androidx.compose.ui.graphics.Color
import com.vegspro.winxdressup.ui.theme.*

enum class ItemCategory {
    HAIR, TOP, BOTTOM, SHOES, WINGS, ACCESSORY, MAKEUP
}

data class OutfitItem(
    val id: String,
    val name: String,
    val category: ItemCategory,
    val color: Color,
    val secondaryColor: Color = color,
    val emoji: String = "✨",
    val roomId: String? = null // null = available in all rooms
)

data class Character(
    val id: String,
    val name: String,
    val nameRu: String,
    val themeColor: Color,
    val emoji: String,
    val description: String
)

data class Room(
    val id: String,
    val name: String,
    val nameRu: String,
    val emoji: String,
    val backgroundColor: Color,
    val unlocked: Boolean = true
)

data class SavedOutfit(
    val id: String,
    val characterId: String,
    val characterName: String,
    val roomId: String,
    val roomName: String,
    val items: Map<ItemCategory, String>, // category -> itemId
    val timestamp: Long = System.currentTimeMillis()
)

object GameData {
    val characters = listOf(
        Character("bloom", "Bloom", "Блум", BloomRed, "🔥", "Фея Огня Дракона"),
        Character("stella", "Stella", "Стелла", StellaYellow, "☀️", "Фея Сияющего Солнца"),
        Character("flora", "Flora", "Флора", FloraGreen, "🌸", "Фея Природы"),
        Character("musa", "Musa", "Муза", MusaPink, "🎵", "Фея Музыки"),
        Character("tecna", "Tecna", "Текна", TecnaCyan, "💻", "Фея Технологий"),
        Character("aisha", "Aisha", "Лейла", AishaBlue, "🌊", "Фея Волн"),
        Character("roxy", "Roxy", "Рокси", RoxyMagenta, "🐾", "Фея Животных"),
        Character("daphne", "Daphne", "Дафна", DaphnePurple, "👑", "Нимфа Сиреникса")
    )

    val rooms = listOf(
        Room("alfea", "Alfea College", "Алфея", "🏰", Color(0xFF4A148C)),
        Room("garden", "Fairy Garden", "Сказочный сад", "🌺", Color(0xFF1B5E20)),
        Room("bedroom", "Magic Bedroom", "Волшебная спальня", "🛏️", Color(0xFF880E4F)),
        Room("cloudtower", "Cloud Tower", "Облачная Башня", "☁️", Color(0xFF311B92)),
        Room("ocean", "Andros Ocean", "Океан Андроса", "🌊", Color(0xFF01579B)),
        Room("stage", "Concert Stage", "Концертная сцена", "🎤", Color(0xFF4A148C), unlocked = true)
    )

    val allItems = listOf(
        // HAIR
        OutfitItem("hair_long", "Long Waves", ItemCategory.HAIR, Color(0xFF5D4037), emoji = "💇‍♀️"),
        OutfitItem("hair_ponytail", "Ponytail", ItemCategory.HAIR, Color(0xFF3E2723), emoji = "ポニー"),
        OutfitItem("hair_buns", "Space Buns", ItemCategory.HAIR, Color(0xFF6D4C41), emoji = "🎀"),
        OutfitItem("hair_short", "Short Bob", ItemCategory.HAIR, Color(0xFF4E342E), emoji = "✂️"),
        OutfitItem("hair_curly", "Curly Magic", ItemCategory.HAIR, Color(0xFF8D6E63), emoji = "🌀"),
        OutfitItem("hair_braids", "Fairy Braids", ItemCategory.HAIR, Color(0xFF5D4037), emoji = "🧶"),
        OutfitItem("hair_glow", "Glowing Locks", ItemCategory.HAIR, Gold, emoji = "✨"),
        OutfitItem("hair_pink", "Pink Dream", ItemCategory.HAIR, PinkPrimary, emoji = "💗"),

        // TOP
        OutfitItem("top_classic", "Classic Crop", ItemCategory.TOP, PinkPrimary, emoji = "👚"),
        OutfitItem("top_sparkle", "Sparkle Top", ItemCategory.TOP, Gold, emoji = "✨"),
        OutfitItem("top_armor", "Fairy Armor", ItemCategory.TOP, AccentCyan, emoji = "🛡️"),
        OutfitItem("top_floral", "Floral Blouse", ItemCategory.TOP, FloraGreen, emoji = "🌺"),
        OutfitItem("top_tech", "Tech Suit", ItemCategory.TOP, TecnaCyan, emoji = "🔷"),
        OutfitItem("top_wave", "Wave Top", ItemCategory.TOP, AishaBlue, emoji = "🌊"),
        OutfitItem("top_rock", "Rock Star", ItemCategory.TOP, MusaPink, emoji = "🎸"),
        OutfitItem("top_royal", "Royal Dress Top", ItemCategory.TOP, DaphnePurple, emoji = "👑"),

        // BOTTOM
        OutfitItem("bottom_skirt", "Mini Skirt", ItemCategory.BOTTOM, PinkLight, emoji = "👗"),
        OutfitItem("bottom_pants", "Magic Pants", ItemCategory.BOTTOM, PurpleLight, emoji = "👖"),
        OutfitItem("bottom_long", "Long Skirt", ItemCategory.BOTTOM, BloomRed, emoji = "👘"),
        OutfitItem("bottom_shorts", "Fairy Shorts", ItemCategory.BOTTOM, StellaYellow, emoji = "🩳"),
        OutfitItem("bottom_leggings", "Sparkle Leggings", ItemCategory.BOTTOM, AccentCyan, emoji = "✨"),
        OutfitItem("bottom_gown", "Ball Gown", ItemCategory.BOTTOM, Gold, emoji = "💃"),

        // SHOES
        OutfitItem("shoes_heels", "High Heels", ItemCategory.SHOES, PinkPrimary, emoji = "👠"),
        OutfitItem("shoes_boots", "Combat Boots", ItemCategory.SHOES, Color(0xFF212121), emoji = "👢"),
        OutfitItem("shoes_sandals", "Fairy Sandals", ItemCategory.SHOES, Gold, emoji = "👡"),
        OutfitItem("shoes_sneakers", "Magic Sneakers", ItemCategory.SHOES, TecnaCyan, emoji = "👟"),
        OutfitItem("shoes_ballet", "Ballet Flats", ItemCategory.SHOES, SoftWhite, emoji = "🩰"),

        // WINGS
        OutfitItem("wings_classic", "Classic Wings", ItemCategory.WINGS, PinkLight, emoji = "🦋"),
        OutfitItem("wings_dragon", "Dragon Wings", ItemCategory.WINGS, BloomRed, emoji = "🐉"),
        OutfitItem("wings_sparkle", "Sparkle Wings", ItemCategory.WINGS, Gold, emoji = "✨"),
        OutfitItem("wings_tech", "Digital Wings", ItemCategory.WINGS, TecnaCyan, emoji = "💠"),
        OutfitItem("wings_nature", "Nature Wings", ItemCategory.WINGS, FloraGreen, emoji = "🍃"),
        OutfitItem("wings_sirenix", "Sirenix Wings", ItemCategory.WINGS, AishaBlue, emoji = "🌊"),
        OutfitItem("wings_believix", "Believix Wings", ItemCategory.WINGS, PurpleLight, emoji = "🌟"),

        // ACCESSORY
        OutfitItem("acc_tiara", "Tiara", ItemCategory.ACCESSORY, Gold, emoji = "👑"),
        OutfitItem("acc_necklace", "Magic Necklace", ItemCategory.ACCESSORY, PinkPrimary, emoji = "📿"),
        OutfitItem("acc_bracelet", "Charm Bracelet", ItemCategory.ACCESSORY, AccentCyan, emoji = "⛓️"),
        OutfitItem("acc_bag", "Fairy Bag", ItemCategory.ACCESSORY, StellaYellow, emoji = "👜"),
        OutfitItem("acc_staff", "Enchanted Staff", ItemCategory.ACCESSORY, DaphnePurple, emoji = "🪄"),
        OutfitItem("acc_glasses", "Tech Glasses", ItemCategory.ACCESSORY, TecnaCyan, emoji = "👓"),
        OutfitItem("acc_headphones", "Music Headphones", ItemCategory.ACCESSORY, MusaPink, emoji = "🎧"),

        // MAKEUP
        OutfitItem("makeup_natural", "Natural Glow", ItemCategory.MAKEUP, SoftWhite, emoji = "😊"),
        OutfitItem("makeup_bold", "Bold Lips", ItemCategory.MAKEUP, BloomRed, emoji = "💄"),
        OutfitItem("makeup_sparkle", "Sparkle Eyes", ItemCategory.MAKEUP, Gold, emoji = "✨"),
        OutfitItem("makeup_fairy", "Fairy Dust", ItemCategory.MAKEUP, PinkPrimary, emoji = "🧚")
    )

    fun itemsForRoom(roomId: String): List<OutfitItem> {
        // Some special items only in certain rooms, but most available everywhere
        return allItems
    }

    fun getDefaultOutfit(characterId: String): Map<ItemCategory, String> {
        return mapOf(
            ItemCategory.HAIR to "hair_long",
            ItemCategory.TOP to "top_classic",
            ItemCategory.BOTTOM to "bottom_skirt",
            ItemCategory.SHOES to "shoes_heels",
            ItemCategory.WINGS to "wings_classic",
            ItemCategory.ACCESSORY to "acc_tiara",
            ItemCategory.MAKEUP to "makeup_natural"
        )
    }
}
