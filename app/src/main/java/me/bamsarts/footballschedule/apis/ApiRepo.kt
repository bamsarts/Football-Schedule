package me.bamsarts.footballschedule.apis

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL

class ApiRepo{
    fun doRequest(url: String): Deferred<String> = GlobalScope.async{
        URL(url).readText()
    }
}