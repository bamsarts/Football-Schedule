package me.bamsarts.footballschedule.utils

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.apis.TheSportDB
import me.bamsarts.footballschedule.model.Match
import me.bamsarts.footballschedule.model.MatchResponse
import me.bamsarts.footballschedule.presenter.ListMatchPresenter
import me.bamsarts.footballschedule.view.ListMatchView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ListMatchPresenterTest {

    @Mock
    private lateinit var view: ListMatchView

    @Mock
    lateinit var apiRepository: ApiRepo

    @Mock
    private lateinit var gson: Gson

    private lateinit var listMatchPresenter: ListMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        listMatchPresenter = ListMatchPresenter(view, apiRepository, gson)
    }

    @Test
    fun testPreviousMatch() {
        val events: MutableList<Match> = mutableListOf()
        val response = MatchResponse(events)
        val matchId = "4328"

        GlobalScope.launch{
            `when`(gson.fromJson(apiRepository.doRequest(TheSportDB.getPreviousMatch(matchId)).await(), MatchResponse::class.java)).thenReturn(response)

            listMatchPresenter.getPreviousMatch(matchId)
            Mockito.verify(view).showEventList(events)
        }

    }

}