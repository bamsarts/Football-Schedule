package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Player

interface DetailPlayerView {

    fun showPlayer(data: List<Player>)
}