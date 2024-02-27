package com.example.myweather

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myweather.data.DayData
import com.example.myweather.data.WeatherData

class CurrentWeatherAdapter(val weatherData:ArrayList<DayData>, activity:FragmentActivity):FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return weatherData.size
    }

    override fun createFragment(position: Int): Fragment {
        return CurrentWeatherFragment(weatherData[position])
    }

    fun updateData(dataList: ArrayList<DayData>){
        weatherData.addAll(dataList)
        notifyDataSetChanged()
    }
}