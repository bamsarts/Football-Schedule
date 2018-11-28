package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Team

interface TeamDetailView {

    fun showTeamDetail(data: List<Team>)
}