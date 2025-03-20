package com.bugbender.gameofthronescharacters.core.utils

import android.content.Context
import com.bugbender.gameofthronescharacters.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ProvideStringRes {

    fun noInternetConnection(): String
    fun adviceForNoInternetConnection(): String

    fun serviceUnavailable(): String
    fun adviceForServiceUnavailable(): String

    class Impl @Inject constructor(
        @ApplicationContext private val context: Context
    ) : ProvideStringRes {

        private fun string(id: Int) = context.resources.getString(id)

        override fun noInternetConnection() = string(R.string.no_internet_connection)
        override fun adviceForNoInternetConnection() =
            string(R.string.advice_for_no_internet_connection)

        override fun serviceUnavailable() = string(R.string.service_unavailable)
        override fun adviceForServiceUnavailable() = string(R.string.advice_for_service_unavailable)
    }
}