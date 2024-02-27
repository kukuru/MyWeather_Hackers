package com.example.myweather

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myweather.databinding.LayoutCityItemBinding

class CityAdapter(val citydata:ArrayList<CityData>, val callback:OnClickListener):
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    inner class CityViewHolder(val binding:LayoutCityItemBinding):ViewHolder(binding.root){
        fun bindHolder(data:CityData){
            binding.name.text = data.name
            binding.name.tag = data.id
            binding.root.setOnClickListener { callback.onClick(binding.root) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutCityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return citydata.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindHolder(citydata[position])
    }
}