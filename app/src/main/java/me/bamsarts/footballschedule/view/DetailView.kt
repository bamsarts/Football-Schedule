package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.model.Team

interface DetailView {

    fun showDetailEvent(data: Match)
    fun showBadgeAway(imgURL: String?)
    fun showBadgeHome(imgURL: String?)
}