package me.bamsarts.footballschedule.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    val idTeam: String? = null,

    @SerializedName("strTeamBadge")
    val strTeamBadge: String? = null,

    @SerializedName("strTeam")
    var strTeam: String? = null,

    @SerializedName("intFormedYear")
    var intFormedYear: String? = null,

    @SerializedName("strStadium")
    var strStadium: String? = null,

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = null
)