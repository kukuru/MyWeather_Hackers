package com.example.myweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myweather.data.DayData
import com.example.myweather.databinding.FragmentFirstBinding
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
const val ICON_URL : String = "https://openweathermap.org/img/wn/%s@2x.png"
class CurrentWeatherFragment(val weatherData:DayData) : Fragment() {
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutWeather.apply {
            cityName.text = weatherData.name
            currentTemp.text = getString(R.string.curremt_temp, weatherData.main.temp.toFloat().toInt())
            descript.text = weatherData.weather[0].description
            cloudy.text = weatherData.clouds.all
            highRowTemp.text = getString(R.string.min_max_temp, weatherData.main.temp_min.toFloat().toInt(),
                weatherData.main.temp_max.toFloat().toInt())

            wind.text = weatherData.wind.speed
            Picasso.get().load(String.format(ICON_URL,weatherData.weather[0].icon)).into(this.weahterIcon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}