package com.app.batman.utilities

import androidx.room.TypeConverter
import com.app.batman.models.Rate
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<Rate>? {
        val listType: Type = object : TypeToken<ArrayList<Rate?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Rate?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}