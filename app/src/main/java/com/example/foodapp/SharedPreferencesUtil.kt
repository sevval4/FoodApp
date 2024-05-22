package com.example.foodapp


import android.content.Context
import com.example.foodapp.model.Besin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesUtil {

    fun saveBesinList(context: Context, key: String, list: List<Besin>) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getBesinList(context: Context, key: String): List<Besin>? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            val type = object : TypeToken<List<Besin>>() {}.type
            gson.fromJson(json, type)
        } else {
            null
        }
    }
}
