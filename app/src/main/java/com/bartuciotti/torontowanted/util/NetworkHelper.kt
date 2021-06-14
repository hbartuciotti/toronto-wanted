package com.bartuciotti.torontowanted.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

/**
 * Network related functions.
 * */
class NetworkHelper @Inject constructor(private val applicationContext: Context) {

    fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}