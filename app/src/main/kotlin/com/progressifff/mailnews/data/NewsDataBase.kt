package com.progressifff.mailnews.data

import android.arch.persistence.room.*
import com.google.gson.Gson
import java.util.*


@Database(entities = [NewsItemDTO::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDataBase : RoomDatabase(){
    abstract fun newsDao(): NewsDao
}

class Converters {
    @TypeConverter
    fun listToJson(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        return objects.toList()
    }

    @TypeConverter
    fun fromTimestamp(value: Long) = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date) = date.time
}