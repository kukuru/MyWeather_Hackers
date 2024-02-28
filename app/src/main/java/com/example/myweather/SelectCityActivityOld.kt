package com.kotlin.myweather

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.kotlin.myweather.data.CityArray
import com.kotlin.myweather.data.CityData
import com.kotlin.myweather.databinding.ActivitySelectCityBinding
import com.kotlin.myweather.db.DBManager
import java.io.InputStreamReader
import kotlin.concurrent.thread

class SelectCityActivityOld : AppCompatActivity() {
    private lateinit var binding: ActivitySelectCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        InputStreamReader(assets.open("areaCode")).use {
            val cityData: CityArray = Gson().fromJson(it, CityArray::class.java)
            val adapter = CityListAdapter(cityData.city){view ->
                val text: TextView = view.findViewById(R.id.city_name) as TextView
                saveData(text.tag as String, text.text as String)
                setResult(RESULT_OK, Intent().apply { putExtra("selected_city", text.tag as String) })
                finish()
            }
            binding.cityList.adapter = adapter
            binding.cityList.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun saveData(cityId:String, name:String) {
        thread {
            DBManager.saveCity(this, CityData(cityId, name))
        }
    }
}