package me.bamsarts.footballschedule.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.content.ContextCompat
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
import me.bamsarts.footballschedule.presenter.DetailMatchPresenter
import me.bamsarts.footballschedule.utils.formatDate
import me.bamsarts.footballschedule.utils.parse
import me.bamsarts.footballschedule.view.DetailView

class MatchDetailActivity : AppCompatActivity(), DetailView {

    private val presenter: DetailMatchPresenter = DetailMatchPresenter(this, this, ApiRepo(), Gson())
    private lateinit var events : Match
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_detail)

        val idEvent = intent.getStringExtra("EVENT_ID")
        val idHome = intent.getStringExtra("HOME_ID")
        val idAway = intent.getStringExtra("AWAY_ID")

        stateFavorite(idEvent)

        presenter.getEventDetail(idEvent)
        presenter.getTeamBadgeHome(idHome)
        presenter.getTeamBadgeAway(idAway)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun showBadgeAway(imgURL: String?) {
        Glide.with(this@MatchDetailActivity).load(imgURL).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(
            imgAway
        )
    }

    override fun showBadgeHome(imgURL: String?) {
        Glide.with(this@MatchDetailActivity).load(imgURL).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(
            imgHome
        )
    }

    override fun showDetailEvent(match: Match) {
        this.events = match

        dateMatch.text = match.eventDate?.formatDate()

        clubHome.text = match.homeTeam
        clubAway.text = match.awayTeam

        scoreHome.text = if(match.homeScore == null) "-" else match.homeScore
        scoreAway.text = if(match.awayScore == null) "-" else match.awayScore

        shotsHome.text = if(match.homeShots == null) "-" else match.homeShots
        shotsAway.text = if(match.awayShots == null) "-" else match.awayShots

        gkHome.text = if(match.homeGK == null) "-" else match.homeGK
        gkAway.text = if(match.awayGK == null) "-" else match.awayGK

        defenseHome.text = if(match.homeDefense == null) "-" else match.homeDefense.parse()
        defenseAway.text = if(match.awayDefense == null) "-" else match.awayDefense.parse()

        midfieldHome.text = if(match.homeMidfield == null) "-" else match.homeMidfield.parse()
        midfieldAway.text = if(match.awayMidfield == null) "-" else match.awayMidfield.parse()

        forwardHome.text = if(match.homeForward == null) "-" else match.homeForward.parse()
        forwardAway.text = if(match.awayForward == null) "-" else match.awayForward.parse()

        subtitutesHome.text = if(match.homeSubtitutes == null) "-" else match.homeSubtitutes.parse()
        subtitutesAway.text = if(match.awaySubtitutes == null) "-" else match.awaySubtitutes.parse()
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
        menuItem = menu
        setFavorite()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.favorite_id -> {
                if (isFavorite) {
                    presenter.deleteFavoriteMatch(events.eventId.toString())

                } else {
                    presenter.saveFavoriteMatch(events)

                }

                isFavorite = !isFavorite
                setFavorite()

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavorite(){
        if(isFavorite){
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fill_heart)
        }else{
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_heart)
        }

    }

    private fun stateFavorite(eventId: String){
        val state = presenter.getFavoriteMatchById(eventId).size
        if(state > 0){
            isFavorite = true
        }
    }

}