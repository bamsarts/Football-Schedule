package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Match

interface DetailView {

//    fun initValue(match: Match)
    fun showDetailEvent(data: Match)

}