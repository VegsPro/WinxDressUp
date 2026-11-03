package com.vegspro.winxdressup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "winx_outfits")

class OutfitRepository(private val context: Context) {
    private val SAVED_OUTFITS_KEY = stringPreferencesKey("saved_outfits")

    val savedOutfits: Flow<List<SavedOutfit>> = context.dataStore.data.map { prefs ->
        val json = prefs[SAVED_OUTFITS_KEY] ?: "[]"
        parseOutfits(json)
    }

    suspend fun saveOutfit(outfit: SavedOutfit) {
        context.dataStore.edit { prefs ->
            val current = parseOutfits(prefs[SAVED_OUTFITS_KEY] ?: "[]").toMutableList()
            current.add(0, outfit) // newest first
            // keep max 50
            val limited = current.take(50)
            prefs[SAVED_OUTFITS_KEY] = toJson(limited)
        }
    }

    suspend fun deleteOutfit(id: String) {
        context.dataStore.edit { prefs ->
            val current = parseOutfits(prefs[SAVED_OUTFITS_KEY] ?: "[]").filter { it.id != id }
            prefs[SAVED_OUTFITS_KEY] = toJson(current)
        }
    }

    private fun toJson(list: List<SavedOutfit>): String {
        val arr = JSONArray()
        list.forEach { o ->
            val obj = JSONObject()
            obj.put("id", o.id)
            obj.put("characterId", o.characterId)
            obj.put("characterName", o.characterName)
            obj.put("roomId", o.roomId)
            obj.put("roomName", o.roomName)
            obj.put("timestamp", o.timestamp)
            val itemsObj = JSONObject()
            o.items.forEach { (cat, itemId) ->
                itemsObj.put(cat.name, itemId)
            }
            obj.put("items", itemsObj)
            arr.put(obj)
        }
        return arr.toString()
    }

    private fun parseOutfits(json: String): List<SavedOutfit> {
        return try {
            val arr = JSONArray(json)
            (0 until arr.length()).map { i ->
                val obj = arr.getJSONObject(i)
                val itemsObj = obj.getJSONObject("items")
                val items = mutableMapOf<ItemCategory, String>()
                itemsObj.keys().forEach { key ->
                    try {
                        items[ItemCategory.valueOf(key)] = itemsObj.getString(key)
                    } catch (_: Exception) {}
                }
                SavedOutfit(
                    id = obj.getString("id"),
                    characterId = obj.getString("characterId"),
                    characterName = obj.getString("characterName"),
                    roomId = obj.getString("roomId"),
                    roomName = obj.getString("roomName"),
                    items = items,
                    timestamp = obj.optLong("timestamp", 0L)
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
