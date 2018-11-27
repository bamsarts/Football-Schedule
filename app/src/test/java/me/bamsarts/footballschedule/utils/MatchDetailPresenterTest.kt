package me.bamsarts.footballschedule.utils

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.bamsarts.footballschedule.apis.ApiRepo
import me.bamsarts.footballschedule.apis.TheSportDB
import me.bamsarts.footballschedule.model.*
import me.bamsarts.footballschedule.view.DetailView
import me.bamsarts.footballschedule.presenter.DetailMatchPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest{

        @Mock
        private lateinit var view: DetailView

        @Mock
        private lateinit var context: Context

        @Mock
        lateinit var apiRepository: ApiRepo

        @Mock
        private lateinit var gson: Gson

        private lateinit var matchDetailPresenter: DetailMatchPresenter

        @Before
        fun setUp(){
            MockitoAnnotations.initMocks(this)
            matchDetailPresenter = DetailMatchPresenter(context, view, apiRepository, gson)
        }

        @Test
        fun testGetEventDetail(){
            val events: MutableList<Match> = mutableListOf()
            val idEvent = "576584"
            val response = EventDetailResponse(events)

            GlobalScope.launch{

                `when`(
                    gson.fromJson(
                        apiRepository.doRequest(TheSportDB.getEventDetail(idEvent)).await(),
                        EventDetailResponse::class.java
                    )

                ).thenReturn(response)

                matchDetailPresenter.getEventDetail(idEvent)
                Mockito.verify(view).showDetailEvent(events)
            }


        }



}