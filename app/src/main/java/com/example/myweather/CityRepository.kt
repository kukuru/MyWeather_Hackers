package com.example.myweather

import android.content.Context
import com.example.myweather.db.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CityRepository(val context: Context) {
    private val currentCity:ArrayList<String> = arrayListOf()
    private val savedCity = flow{
        AppDataBase.getInstance(context).cityDao().queryCityData().forEach {
            emit(it.id)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getSavedCity():ArrayList<String>{
        savedCity.collect{
            if(currentCity.contains(it)){
                currentCity.remove(it)
            }
            else{
                currentCity.add(it)
            }
        }
        return currentCity
    }

    suspend fun saveCity(data:CityData) = withContext(Dispatchers.IO){
        AppDataBase.getInstance(context).cityDao().insertCityData(data)
    }
}