package com.example.myweather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myweather.CityData

@Database(entities = [CityData::class], version=1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun cityDao():CityDao
    companion object{
        private var INSTANCE:AppDataBase?  = null
        fun getInstance(context: Context) = INSTANCE?: synchronized(this){
            val instance = Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "weather.db").build()
            INSTANCE = instance
            return instance
        }
    }
}