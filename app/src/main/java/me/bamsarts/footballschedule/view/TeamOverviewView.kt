package me.bamsarts.footballschedule.view

import me.bamsarts.footballschedule.model.Team

interface TeamOverviewView {

    fun showTeamDetail(data: List<Team>)
}