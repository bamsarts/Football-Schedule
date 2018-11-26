package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Match

interface ListMatchView {

    fun showEventList(data: List<Match>)
}