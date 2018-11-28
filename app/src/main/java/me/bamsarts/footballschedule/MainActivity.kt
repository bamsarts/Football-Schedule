package me.bamsarts.footballschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import me.bamsarts.footballschedule.R.id.teamsNavigator
import me.bamsarts.footballschedule.fragment.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navbar.setOnNavigationItemSelectedListener {
            item -> when(item.itemId){
                R.id.matchNavigator-> {
                    openFragment(MatchFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.teamsNavigator -> {
                    openFragment(TeamsFragment())
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.favouriteNavigator -> {
                    openFragment(FavoriteMatchFragment())
                    return@setOnNavigationItemSelectedListener true

                }
            }
            true
        }
        navbar.selectedItemId = teamsNavigator
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.listLayout, fragment)
            .addToBackStack(null)
            .commit()
    }


}