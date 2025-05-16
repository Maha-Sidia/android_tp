package com.example.tp6.data

    data class WeatherData(
    val sys : Sys,
            val main: Main,
                    val weather:List<weather>,
                            val wind: wind
            )
    data class Main(
        val temp:Double,
        val humidity:Int
    )
    data class Sys(
        val country :String

    )
    data class wind(
        val speed:Double

    )
    data class weather(
        val description:String

    )


