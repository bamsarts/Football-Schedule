package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Match

interface DetailView {

//    fun initValue(match: Match)
    fun showDetailEvent(data: Match)
    fun showBadgeAway(imgURL: String?)
    fun showBadgeHome(imgURL: String?)
}