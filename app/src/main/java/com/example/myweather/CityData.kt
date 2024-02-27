package com.example.myweather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class CityList(var city:ArrayList<CityData>)
@Entity(tableName = "city_list")
data class CityData(@PrimaryKey var id:String, @ColumnInfo var name:String)