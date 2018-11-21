package me.bamsarts.footballschedule.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.APIs.ApiRepository
import me.bamsarts.footballschedule.APIs.SportDB
import me.bamsarts.footballschedule.model.TeamResponses
import kotlinx.android.synthetic.main.match_detail.*
import me.bamsarts.footballschedule.APIs.ApiRepo
import me.bamsarts.footballschedule.R.id.*
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.presenter.MatchDetailPresenter
import me.bamsarts.footballschedule.utils.formatDate
import me.bamsarts.footballschedule.utils.parse
import me.bamsarts.footballschedule.view.DetailView
import org.jetbrains.anko.matchParent

class MatchDetailActivity : AppCompatActivity(), DetailView {

    private val presenter: MatchDetailPresenter = MatchDetailPresenter(this, this, ApiRepo(), Gson())
//    private var idEvent : String? = ""
    private lateinit var event : Match
    private var isFavorite: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_detail)

        val idEvent = intent.getStringExtra("EVENT_ID")
        val idHome = intent.getStringExtra("HOME_ID")
        val idAway = intent.getStringExtra("AWAY_ID")

        stateFavorite(idEvent)

        presenter.getEventDetail(idEvent)

        ApiRepository.retrofit
            .create(SportDB::class.java)
            .getClubDetailById(idHome)
            .enqueue(object : Callback<TeamResponses> {
                override fun onFailure(call: Call<TeamResponses>, t: Throwable) {

                }

                override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                    val imgUrl = response.body()?.teams?.get(0)?.strTeamBadge
                    Glide.with(this@MatchDetailActivity).load(imgUrl).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(imgHome)
                }

            })

        ApiRepository.retrofit.create(SportDB::class.java)
            .getClubDetailById(idAway)
            .enqueue(object : Callback<TeamResponses> {
                override fun onFailure(call: Call<TeamResponses>, t: Throwable) {

                }

                override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                    val imgUrl = response.body()?.teams?.get(0)?.strTeamBadge
                    Glide.with(this@MatchDetailActivity).load(imgUrl).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(imgAway)
                }

            })


        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

    }

//    override fun initValue(match: Match){
//        this.event = match
//
//        dateMatch.text = match.eventDate
//        clubHome.text = match.homeTeam
//        clubAway.text = match.awayTeam
//        scoreHome.text = match.homeScore
//        scoreAway.text = match.awayScore
//        gkHome.text = match.homeGK
//        gkAway.text = match.awayGK
//        defenseHome.text = match.homeDefense
//        defenseAway.text = match.awayDefense
//        midfieldHome.text = match.homeMidfield
//        midfieldAway.text = match.awayMidfield
//        forwardHome.text = match.homeForward
//        forwardAway.text = match.awayForward
//        subtitutesHome.text = match.homeSubtitutes
//        subtitutesAway.text = match.awaySubtitutes
//        shotsHome.text = match.homeShots
//        shotsAway.text = match.awayShots
//    }

    override fun showDetailEvent(match: Match) {
        this.event = match

        dateMatch.text = match.eventDate?.formatDate()

        clubHome.text = match.homeTeam
        clubAway.text = match.awayTeam

        scoreHome.text = match.homeScore
        scoreAway.text = match.awayScore

        shotsHome.text = match.homeShots
        shotsAway.text = match.awayShots

        gkHome.text = match.homeGK
        gkAway.text = match.awayGK

        defenseHome.text = match.homeDefense?.parse()
        defenseAway.text = match.awayDefense?.parse()

        midfieldHome.text = match.homeMidfield?.parse()
        midfieldAway.text = match.awayMidfield?.parse()

        forwardHome.text = match.homeForward?.parse()
        forwardAway.text = match.awayForward?.parse()

        subtitutesHome.text = match.homeSubtitutes?.parse()
        subtitutesAway.text = match.awaySubtitutes?.parse()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)

        val item = menu?.findItem(R.id.favorite_id)
        if (isFavorite) {
            item?.setIcon(R.drawable.ic_fill_heart)
        } else {
            item?.setIcon(R.drawable.ic_heart)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.favorite_id -> {
                if (isFavorite) {
                    presenter.deleteFavoriteMatch(event.eventId.toString())
                    item.setIcon(R.drawable.ic_heart)
                } else {
                    presenter.saveFavoriteMatch(event)
                    item.setIcon(R.drawable.ic_fill_heart)
                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun stateFavorite(eventId: String){
        val state = presenter.getFavoriteMatchById(eventId).size
        if(state > 0){
            isFavorite = true
        }
    }

}