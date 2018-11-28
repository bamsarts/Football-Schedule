package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Match

interface SearchMatchView {
    fun showMatchList(data: List<Match>)
}