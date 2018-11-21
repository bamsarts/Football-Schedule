package me.bamsarts.footballschedule.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("strDate")
    var eventDate: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,

    @SerializedName("idHomeTeam")
    var homeId: String? = null,

    @SerializedName("idAwayTeam")
    var awayId: String? = null,

    @SerializedName("intHomeShots")
    val homeShots: String? = null,

    @SerializedName("intAwayShots")
    val awayShots: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    val homeGK: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    val awayGK: String? = null,

    @SerializedName("strHomeLineupDefense")
    val homeDefense: String? = null,

    @SerializedName("strAwayLineupDefense")
    val awayDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    val homeMidfield: String? = null,

    @SerializedName("strAwayLineupMidfield")
    val awayMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    val homeForward: String? = null,

    @SerializedName("strAwayLineupForward")
    val awayForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    val homeSubtitutes: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    val awaySubtitutes: String? = null
) : Parcelable