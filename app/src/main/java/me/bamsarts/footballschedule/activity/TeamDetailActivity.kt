package me.bamsarts.footballschedule.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.adapter.ViewPagerAdapter
import me.bamsarts.footballschedule.fragment.PlayersFragment
import me.bamsarts.footballschedule.fragment.TeamOverviewFragment
import me.bamsarts.footballschedule.model.Team
import me.bamsarts.footballschedule.presenter.TeamDetailPresenter
import me.bamsarts.footballschedule.view.TeamDetailView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.toast
import kotlinx.android.synthetic.main.activity_team_detail.*
import me.bamsarts.footballschedule.R.id.favorite_id
import me.bamsarts.footballschedule.R.menu.favorite
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.db.FavouriteTeam
import me.bamsarts.footballschedule.db.database
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.delete

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team
    private lateinit var progressBar: ProgressBar

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String
    private lateinit var nameT: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val intent = intent
        id = intent.getStringExtra("id")
        nameT = intent.getStringExtra("teamName")

        val bundle = Bundle()
        bundle.putString("teamId", id)

        supportActionBar?.title = nameT
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        val teamOverviewFragment = TeamOverviewFragment()
        val playersFragment = PlayersFragment()

        teamOverviewFragment.arguments = bundle
        playersFragment.arguments = bundle

        adapter.populateFragment(teamOverviewFragment, "Team Overview")
        adapter.populateFragment(playersFragment, "Players")

        team_viewpager.adapter = adapter
        team_tabs.setupWithViewPager(team_viewpager)

        progressBar = teamProgressBar


        favoriteState()
        val request = ApiRepo()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)


    }

    private fun favoriteState(){
        database.use {
            val result = select(FavouriteTeam.TABLE_TEAM_FAVORITE)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<FavouriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showTeamDetail(data: List<Team>) {
        teams = Team(data[0].idTeam,
            data[0].strTeam,
            data[0].strTeamBadge)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            favorite_id -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(FavouriteTeam.TABLE_TEAM_FAVORITE,
                    FavouriteTeam.TEAM_ID to teams.idTeam,
                    FavouriteTeam.TEAM_NAME to teams.strTeam,
                    FavouriteTeam.TEAM_BADGE to teams.strTeamBadge)
            }
            toast("Added to favorite")

        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavouriteTeam.TABLE_TEAM_FAVORITE, "(TEAM_ID = {id})",
                    "id" to id)
            }
            toast("Removed to favorite")
        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fill_heart)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_heart)
    }

}