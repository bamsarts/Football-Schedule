package me.bamsarts.footballschedule.model

import com.google.gson.annotations.SerializedName

data class SearchMatchResponse(
    @SerializedName("event")
    val event: List<Match>
)