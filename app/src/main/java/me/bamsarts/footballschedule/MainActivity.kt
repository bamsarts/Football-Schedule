package me.bamsarts.footballschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import me.bamsarts.footballschedule.fragment.FavoriteMatchFragment
import me.bamsarts.footballschedule.fragment.NextMatchFragment
import me.bamsarts.footballschedule.fragment.PreviousMatchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navbar.setOnNavigationItemSelectedListener {
            item -> when(item.itemId){
                R.id.previousNavigator-> {
                    openFragment(PreviousMatchFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nextNavigator -> {
                    openFragment(NextMatchFragment())
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.favouriteNavigator -> {
                    openFragment(FavoriteMatchFragment())
                    return@setOnNavigationItemSelectedListener true

                }
            }
            false

        }

        openFragment(PreviousMatchFragment())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.listLayout, fragment)
            .addToBackStack(null)
            .commit()
    }


}