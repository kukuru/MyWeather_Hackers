package com.example.myweather

import com.example.myweather.data.DayData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedInputStream
import java.net.URL

class WeatherRemoteDataSource (private val cityList:ArrayList<String>){
    private val dataFlow: Flow<DayData> = flow{
        val url = "https://api.openweathermap.org/data/2.5/weather?appid=2677d0da8acab2b1d91d54942d6914c8&units=metric&id="
        cityList.forEach{
            val connect = URL(url+it).openConnection()
            var weather:String
            BufferedInputStream(connect.getInputStream()).use {
                weather = it.reader().readText()
            }
            emit(Gson().fromJson(weather, DayData::class.java))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun loadWeatherData() = arrayListOf<DayData>().apply{
            dataFlow.collect{
                this.add(it)
            }
        }
}