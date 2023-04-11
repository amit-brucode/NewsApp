package com.example.newsapp.ui.db

import androidx.room.TypeConverter

class Convertors {
    @TypeConverter
    fun fromSource(source: com.example.newsapp.ui.models.Source): String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): com.example.newsapp.ui.models.Source {
        return com.example.newsapp.ui.models.Source(name, name)
    }
}