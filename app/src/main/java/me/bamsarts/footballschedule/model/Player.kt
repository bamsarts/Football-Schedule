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
    var strCutout: String? = null

)