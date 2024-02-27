package com.example.myweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweather.databinding.ActivitySelectCityBinding
import com.example.myweather.db.AppDataBase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.concurrent.thread

const val SELECT_ID = "select_id"
class SelectCityActivity : AppCompatActivity() {
    lateinit var binding:ActivitySelectCityBinding
    val viewModel:SelectCityViewModel by viewModels<SelectCityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.cityListView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                binding.cityListView.adapter = CityAdapter(viewModel.getCityList().city){ root->
                    val data = root.findViewById<TextView>(R.id.name).let { textview->
                        CityData(textview.tag as String,textview.text.toString())
                    }
                    lifecycleScope.launch {
                        viewModel.saveCityData(data)
                    }

                    setResult(RESULT_OK, Intent().putExtra(SELECT_ID, data.id))
                    finish()
                }
            }
        }
    }

}