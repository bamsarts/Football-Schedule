package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Match

interface DetailView {

    fun showDetailEvent(match: List<Match>)
    fun showBadgeAway(imgURL: String?)
    fun showBadgeHome(imgURL: String?)
}