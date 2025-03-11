package com.bugbender.gameofthronescharacters.character.data

import com.bugbender.gameofthronescharacters.core.ProvideStringRes
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class HandleErrorImplTest {

    private lateinit var handleError: HandleError
    private lateinit var provideStringRes: ProvideStringRes

    @Before
    fun setUp() {
        provideStringRes = FakeProvideStringRes()
        handleError = HandleError.Impl(provideStringRes)
    }

    @Test
    fun allScenarios() {
        var actualMessage = handleError.handle(UnknownHostException())
        assertEquals("noInternetConnection", actualMessage)

        actualMessage = handleError.handle(Exception())
        assertEquals("serviceUnavailableTryLater", actualMessage)
    }


    class FakeProvideStringRes : ProvideStringRes {

        override fun noInternetConnection() = "noInternetConnection"

        override fun serviceUnavailableTryLater() = "serviceUnavailableTryLater"
    }
}