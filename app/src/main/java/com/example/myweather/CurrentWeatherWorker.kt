package com.example.myweather

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myweather.data.WeekList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedInputStream
import java.net.URL
const val WEATHER_DATA = "weather_data"
class CurrentWeatherWorker(context: Context, val param:WorkerParameters):Worker(context, param) {
    override fun doWork(): Result {
        val url = "https://api.openweathermap.org/data/2.5/weather?appid=2677d0da8acab2b1d91d54942d6914c8&units=metric&id="
        val id = param.inputData.getStringArray(SELECT_ID)
        val weatherList = ArrayList<String>()
        repeat(id?.size?:0){
            val connect = URL(url+id!![it]).openConnection()
            var weather:String
            BufferedInputStream(connect.getInputStream()).use {
                weather = it.reader().readText()
            }
            weatherList.add(weather)
        }

        return Result.success(Data.Builder().putStringArray(WEATHER_DATA, weatherList.toTypedArray()).build())
    }
}