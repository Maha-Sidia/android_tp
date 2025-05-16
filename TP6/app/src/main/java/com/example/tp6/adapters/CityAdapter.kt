package com.example.tp6.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp6.R

class CityAdapter(private val cityList:Array<String>, private val citylistener: OnCityClickListener) :RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    class CityViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        val textView:TextView=view.findViewById(R.id.city)
        fun bind(city:String,listener:OnCityClickListener){
            textView.setOnClickListener{
                listener.onCityClicker(city)
            }

        }
    }
    interface OnCityClickListener{
        fun onCityClicker(city:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val adapterLayout=LayoutInflater.from(parent.context)
            .inflate(R.layout.city_item,parent,false)
        return CityViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.textView.text=cityList[position]
        holder.bind(cityList[position],citylistener)
    }
}