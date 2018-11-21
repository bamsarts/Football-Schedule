package me.bamsarts.footballschedule.APIs

interface ApiRepositoryCallback<T>{
    fun onResponse(data: T?)
    fun onFailure()
}