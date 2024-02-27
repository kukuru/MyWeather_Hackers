package com.example.myweather

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.myweather.data.DayData
import com.example.myweather.databinding.ActivityMainBinding
import com.example.myweather.db.AppDataBase
import com.google.gson.Gson
import kotlin.concurrent.thread

class MainActivityOld : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val registry = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        when(it.resultCode){
            RESULT_OK->{
                val id = it.data?.getStringExtra(SELECT_ID)
                if(id != null){
                    requestWorker(arrayListOf(id))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.viewPager.adapter = CurrentWeatherAdapter(arrayListOf(), this)
        getSavedCity{
            runOnUiThread {
                if(it.size > 0){
                    requestWorker(it)
                }
            }
        }
    }

    fun getSavedCity(callback:(ArrayList<String>)->Unit){
        thread {
            val ids = ArrayList<String>()
            AppDataBase.getInstance(this).cityDao().queryCityData().forEach { ids.add(it.id) }
            callback(ids)
        }
    }

    fun requestWorker(ids:List<String>){
        val data = Data.Builder().putStringArray(SELECT_ID, ids.toTypedArray()).build()
        val request = OneTimeWorkRequestBuilder<CurrentWeatherWorker>().setInputData(data).build()
        WorkManager.getInstance(this).enqueue(request)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id)
            .observe(this) { info ->
                if (info.state == WorkInfo.State.SUCCEEDED) {
                    val dataList = ArrayList<DayData>()
                    info.outputData.getStringArray(WEATHER_DATA)?.forEach {
                        dataList.add(Gson().fromJson(it, DayData::class.java))
                    }
                    (binding.viewPager.adapter as CurrentWeatherAdapter).updateData(dataList)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_select_city -> {
                registry.launch(Intent(this, SelectCityActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}