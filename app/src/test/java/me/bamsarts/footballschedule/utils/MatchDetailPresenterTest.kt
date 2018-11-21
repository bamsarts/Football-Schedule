package me.bamsarts.footballschedule.utils

import android.content.Context
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import me.bamsarts.footballschedule.APIs.ApiRepositoryCallback
import me.bamsarts.footballschedule.view.DetailView
import me.bamsarts.footballschedule.model.MatchesResponses
import me.bamsarts.footballschedule.presenter.MatchDetailPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest{


        @Mock
        private lateinit var view = DetailView

        @Mock
        private val context = Context

        private lateinit var matchDetailPresenter: MatchDetailPresenter

        @Before
        fun setUp(){
            MockitoAnnotations.initMocks(this)
            matchDetailPresenter = MatchDetailPresenter(Context, DetailView)
        }

        @Test
        fun getMatchResponseTest(){
            val idEvent = "576584"
            matchDetailPresenter.getClubDetailById(idEvent)

            argumentCaptor<ApiRepositoryCallback<MatchesResponses>>().apply {
                verify()
            }
        }


}