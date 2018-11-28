package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Team

interface TeamsView {

    fun showTeamList(data: List<Team>)
}