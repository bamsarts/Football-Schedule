package me.bamsarts.footballschedule.APIs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

object ApiRepository {

        val BASE_URL = "https://www.thesportsdb.com"
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
}

class ApiRepo{
    fun doRequest(url: String) : String{
        return URL(url).readText()
    }
}