package me.bamsarts.footballschedule.model

import com.google.gson.annotations.SerializedName

data class MatchesResponses(
    @SerializedName("events") var events: MutableList<Match>)