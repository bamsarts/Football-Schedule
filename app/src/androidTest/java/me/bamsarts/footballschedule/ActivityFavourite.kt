package me.bamsarts.footballschedule

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import me.bamsarts.footballschedule.R.id.*


class ActivityFavourite{

    @Rule
    @JvmField
    var ruleActivity = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addFavouriteBehaviour(){
        Thread.sleep(4000)
        onView(withId(idPreviousMatch)).check(matches(isDisplayed()))
        onView(withId(idPreviousMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
        onView(withId(idPreviousMatch)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4,
                ViewActions.click()
            ))
        Thread.sleep(4000)

        onView(withId(favorite_id)).perform(ViewActions.click())

        Espresso.pressBack()
        Thread.sleep(4000)
        onView(withId(navbar)).check(matches(isDisplayed()))
        onView(withId(nextNavigator)).perform(ViewActions.click())

        Thread.sleep(4000)
        onView(withId(idNextMatch)).check(matches(isDisplayed()))
        onView(withId(idNextMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))
        onView(withId(idNextMatch)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(8,
                ViewActions.click()
            ))
        Thread.sleep(4000)

        onView(withId(favorite_id)).perform(ViewActions.click())
        Espresso.pressBack()

        Thread.sleep(4000)
        onView(withId(navbar)).check(matches(isDisplayed()))
        onView(withId(favouriteNavigator)).perform(ViewActions.click())
        Thread.sleep(5000)
    }
}