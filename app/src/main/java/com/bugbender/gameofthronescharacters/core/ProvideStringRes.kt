package com.bugbender.gameofthronescharacters.core

import android.content.Context
import com.bugbender.gameofthronescharacters.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ProvideStringRes {

    fun noInternetConnection(): String

    fun serviceUnavailableTryLater(): String

    class Impl @Inject constructor(
        @ApplicationContext private val context: Context
    ) : ProvideStringRes {

        private fun string(id: Int) = context.resources.getString(id)

        override fun noInternetConnection() = string(R.string.no_internet_connection)

        override fun serviceUnavailableTryLater() = string(R.string.service_unavailable_try_later)
    }
}