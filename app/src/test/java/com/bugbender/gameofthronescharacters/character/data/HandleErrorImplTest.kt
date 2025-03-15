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
    fun `test handling UnknownHostException`() {
        // Given
        val exception = UnknownHostException()

        // When
        val (actualMessage, actualAdvice) = handleError.handle(exception)

        // Then
        assertEquals("noInternetConnection", actualMessage)
        assertEquals(
            "Your device does not seem to have access to the internet.\nPlease try again!",
            actualAdvice
        )
    }

    @Test
    fun `test handling generic Exception`() {
        // Given
        val exception = Exception()

        // When
        val (actualMessage, actualAdvice) = handleError.handle(exception)

        // Then
        assertEquals("serviceUnavailableTryLater", actualMessage)
        assertEquals(
            "The service is temporarily unavailable.\nPlease try again later!",
            actualAdvice
        )
    }

    // Fake implementation of ProvideStringRes
    class FakeProvideStringRes : ProvideStringRes {

        override fun noInternetConnection() = "noInternetConnection"

        override fun adviceForNoInternetConnection() =
            "Your device does not seem to have access to the internet.\nPlease try again!"

        override fun serviceUnavailable() = "serviceUnavailableTryLater"

        override fun adviceForServiceUnavailable() =
            "The service is temporarily unavailable.\nPlease try again later!"
    }
}
