package com.bartuciotti.torontowanted.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

/**
 * Broadcast receiver to watch the network state (connected to the internet or not).
 * Other classes can extend this Listener to observe the state.
 * */
class NetworkStateReceiver(listener: ConnectivityReceiverListener) : BroadcastReceiver() {


    private val mConnectivityReceiverListener: ConnectivityReceiverListener = listener


    override fun onReceive(context: Context, intent: Intent?) {
        mConnectivityReceiverListener.onNetworkConnectionChanged(isConnected(context))
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    private fun isConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}