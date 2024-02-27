package com.example.myweather

class WeatherRepository {
    suspend fun loadData(savedCityList:ArrayList<String>) = WeatherRemoteDataSource(savedCityList).loadWeatherData()
}