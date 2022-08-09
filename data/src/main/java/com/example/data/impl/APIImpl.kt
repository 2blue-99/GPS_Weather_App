package com.example.data.impl

import android.util.Log
import com.example.data.BuildConfig
import com.example.data.data_source.API
import com.example.data.dto.Weather
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIImpl: API {

    private val BASE_URL = BuildConfig.BASE_URL

    override suspend fun getWeatherData(data: HashMap<String, String>): Weather {
        Log.e(javaClass.simpleName, "Input_Data: $data", )
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Retrofit Object Create
            .create(API::class.java).getWeatherData(data)
    }


}