package com.bugbender.gameofthronescharacters.character.data

import com.bugbender.gameofthronescharacters.core.ProvideStringRes
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

interface HandleError {

    fun handle(e: Exception): String

    @Singleton
    class Impl @Inject constructor(private val provideStringRes: ProvideStringRes) : HandleError {

        override fun handle(e: Exception): String = with(provideStringRes) {
            if (e is UnknownHostException) {
                noInternetConnection()
            } else {
                serviceUnavailableTryLater()
            }
        }
    }
}