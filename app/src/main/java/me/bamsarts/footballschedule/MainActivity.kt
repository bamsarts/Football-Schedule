package me.bamsarts.footballschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import me.bamsarts.footballschedule.R.id.navbar
import me.bamsarts.footballschedule.fragment.FavoriteMatchFragment
import me.bamsarts.footballschedule.fragment.NextFragment
import me.bamsarts.footballschedule.fragment.PrevFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navbar.setOnNavigationItemSelectedListener {
            item -> when(item.itemId){
                R.id.navigation_prev-> {
                    openFragment(PrevFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_next -> {
                    openFragment(NextFragment())
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.navigation_fav -> {
                    openFragment(FavoriteMatchFragment())
                    return@setOnNavigationItemSelectedListener true

                }
            }
            false

        }

        openFragment(PrevFragment())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.listLayout, fragment)
            .addToBackStack(null)
            .commit()
    }


}