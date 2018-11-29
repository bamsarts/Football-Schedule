package me.bamsarts.footballschedule.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("idTeam")
    var idTeam: String? = null,

    @SerializedName("idPlayer")
    var idPlayer: String? = null,

    @SerializedName("strPlayer")
    var strPlayer: String? = null,

    @SerializedName("strDescriptionEN")
    var strDescriptionEN: String? = null,

    @SerializedName("strCutout")
    var strCutout: String? = null,

    @SerializedName("strThumb")
    var strThumb: String? = null,

    @SerializedName("strFanart1")
    var strFanart1: String? = null

)