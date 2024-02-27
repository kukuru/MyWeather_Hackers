package com.example.myweather

import android.app.Application
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class SelectCityViewModel(private val application: Application):AndroidViewModel(application) {
    val cityRepository = CityRepository(application)
    suspend fun getCityList() = withContext(Dispatchers.IO){
        InputStreamReader(application.assets.open("areaCode")).use{
            return@withContext Gson().fromJson(it, CityList::class.java)
        }
    }

    suspend fun saveCityData(data:CityData){
        viewModelScope.launch{
            cityRepository.saveCity(data)
        }.join()
    }
}