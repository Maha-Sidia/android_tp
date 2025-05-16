package com.example.tp6.service

import com.example.tp6.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
        @GET("weather")
        suspend fun getCurrentWeather(
            @Query("q") city: String,
            @Query("appid")apiKey: String ="",
            @Query("units") units : String ="metric",
            @Query("lang") lang: String  ="fr"

        ): WeatherData
}