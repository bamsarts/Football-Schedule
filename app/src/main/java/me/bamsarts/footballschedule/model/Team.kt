package me.bamsarts.footballschedule.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    val idTeam: String? = null,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String? = null
)