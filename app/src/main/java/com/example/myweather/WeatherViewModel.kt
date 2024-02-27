package com.example.myweather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

enum class UISTATE{
    INIT,
    FRESH,
    DONE
}
class WeatherViewModel(application: Application):AndroidViewModel(application) {
    val cityRepository = CityRepository(application)
    val uistate = MutableStateFlow(UISTATE.INIT)
    val weatherRepository = WeatherRepository()
    private val weatherData = liveData{
        uistate.collect{
            if(uistate.value != UISTATE.DONE){
                this.emit(weatherRepository.loadData(cityRepository.getSavedCity()))
                uistate.update { UISTATE.DONE }
            }
        }
    }

    fun loadCurrentWeatherData() = weatherData
}