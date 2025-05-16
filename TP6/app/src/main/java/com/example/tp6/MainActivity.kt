package com.example.tp6

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.tp6.adapters.CityAdapter
import com.example.tp6.service.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),CityAdapter.OnCityClickListener {
    private val cities = arrayOf("Tunis", "Paris", "Londres", "Pekin",
        "Tokyo", "Ottawa", "Alger", "Moscou", "Berlin", "Madrid", "Montevideo",
        "Buenos Aires")
    private val baseUrl="https://api.openweathermap.org/data/2.5"
    lateinit var retrofit: Retrofit
    lateinit var weatherApiService: WeatherApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // val binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            . addConverterFactory (GsonConverterFactory. create ( ) )
            . build()
        weatherApiService = retrofit. create(weatherApiService::class.java)
        var recycler:RecyclerView = findViewById(R.id.Recycle_view) as RecyclerView
        recycler.adapter=CityAdapter(cities,this)
    }

    override fun onCityClicker(city: String) {
      //  Toast.makeText( this,city, Toast.LENGTH_SHORT).show()
        getWeatherData(city)
    }
    private fun getWeatherData(city: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = weatherApiService.getCurrentWeather(city)
                withContext(Dispatchers.Main) {
                    // Ici, Affichez les résultats dans les objets de l'interface
                    findViewById<TextView>(R.id.pays).text = "Pays : ${response.sys.country}"
                    findViewById<TextView>(R.id.température).text = "Température : ${response.main.temp}°C"
                    findViewById<TextView>(R.id.humidité).text = "Humidité : ${response.main.humidity}%"
                    findViewById<TextView>(R.id.vitesse).text = "Vitesse du vent : ${response.wind.speed} km/h"
                    findViewById<TextView>(R.id.description).text = "Description : ${response.weather[0].description}"
                    Toast.makeText(
                        applicationContext,
                        "Données météorologiques rendues avec SUCCES...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "Introuvable !!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}