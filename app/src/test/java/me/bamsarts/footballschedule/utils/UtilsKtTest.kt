package me.bamsarts.footballschedule.utils

import me.bamsarts.footballschedule.APIs.ApiRepo
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.text.SimpleDateFormat

class UtilsKtTest {

    @Test
    fun toSimpleString() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("02/28/2018")
        assertEquals("Wed, 28 Feb 2018", toSimpleString(date))
    }

    @Test
    fun testDoRequest(){
        val apiRepository = mock(ApiRepo::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}