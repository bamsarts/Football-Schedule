package me.bamsarts.footballschedule.APIs

import java.net.URL

class ApiRepo{
    fun doRequest(url: String) : String{
        return URL(url).readText()
    }
}