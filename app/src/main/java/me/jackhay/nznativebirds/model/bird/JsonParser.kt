package me.jackhay.nznativebirds.model.bird

import android.content.Context
import com.google.gson.Gson
import me.jackhay.nznativebirds.R

class  JsonParser(private val context: Context) {

    fun getBirdsFromJson(): BirdDatabaseDto? {
        val text = context.resources.openRawResource(R.raw.database)
            .bufferedReader().use { it.readText() }
        return Gson().fromJson(text, BirdDatabaseDto::class.java)
    }
}