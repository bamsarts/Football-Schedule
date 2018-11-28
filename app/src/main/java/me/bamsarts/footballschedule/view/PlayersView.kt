package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Player


interface PlayersView {

    fun showPlayerList(data: List<Player>)
}