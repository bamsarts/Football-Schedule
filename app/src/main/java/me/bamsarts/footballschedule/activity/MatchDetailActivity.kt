package me.bamsarts.footballschedule.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import me.bamsarts.footballschedule.R
import kotlinx.android.synthetic.main.match_detail.*
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.presenter.DetailMatchPresenter
import me.bamsarts.footballschedule.utils.formatDate
import me.bamsarts.footballschedule.utils.parse
import me.bamsarts.footballschedule.view.DetailView
import org.jetbrains.anko.toast

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

    override fun showDetailEvent(match: List<Match>) {
        events = Match(
            match[0].eventId,
            match[0].eventDate,
            match[0].homeTeam,
            match[0].awayTeam,
            match[0].homeScore,
            match[0].awayScore,
            match[0].homeId,
            match[0].awayId
        )

        dateMatch.text = match[0].eventDate?.formatDate()

        clubHome.text = match[0].homeTeam
        clubAway.text = match[0].awayTeam

        scoreHome.text = if(match[0].homeScore == null) "-" else match[0].homeScore
        scoreAway.text = if(match[0].awayScore == null) "-" else match[0].awayScore

        shotsHome.text = if(match[0].homeShots == null) "-" else match[0].homeShots
        shotsAway.text = if(match[0].awayShots == null) "-" else match[0].awayShots

        gkHome.text = if(match[0].homeGK == null) "-" else match[0].homeGK
        gkAway.text = if(match[0].awayGK == null) "-" else match[0].awayGK

        defenseHome.text = if(match[0].homeDefense == null) "-" else match[0].homeDefense?.parse()
        defenseAway.text = if(match[0].awayDefense == null) "-" else match[0].awayDefense?.parse()

        midfieldHome.text = if(match[0].homeMidfield == null) "-" else match[0].homeMidfield?.parse()
        midfieldAway.text = if(match[0].awayMidfield == null) "-" else match[0].awayMidfield?.parse()

        forwardHome.text = if(match[0].homeForward == null) "-" else match[0].homeForward?.parse()
        forwardAway.text = if(match[0].awayForward == null) "-" else match[0].awayForward?.parse()

        subtitutesHome.text = if(match[0].homeSubtitutes == null) "-" else match[0].homeSubtitutes?.parse()
        subtitutesAway.text = if(match[0].awaySubtitutes == null) "-" else match[0].awaySubtitutes?.parse()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
                if (this::events.isInitialized && isFavorite) {
                    presenter.deleteFavoriteMatch(events.eventId.toString())

                } else if(this::events.isInitialized) {
                    presenter.saveFavoriteMatch(events)
                }else{
                    toast("Data Belum Siap, Periksa Internet Anda")
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