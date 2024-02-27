package com.example.myweather.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myweather.CityData

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCityData(data:CityData)

    @Query("select * from city_list")
    fun queryCityData():List<CityData>

    @Query("select * from city_list where id = :id")
    fun queryCityDataById(id:String):CityData
}