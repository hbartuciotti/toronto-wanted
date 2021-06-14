package com.bartuciotti.torontowanted.util

import android.content.SharedPreferences
import com.bartuciotti.torontowanted.data.Api
import com.bartuciotti.torontowanted.data.Database
import com.bartuciotti.torontowanted.pages.investigations.data.Announcement
import com.bartuciotti.torontowanted.util.Constants.REMOTE_CONFIG
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.json.JSONObject
import javax.inject.Inject

/**
 * Class responsible for handling the RemoteConfig file.
 * This file is fetched from the api on startup to check for announcements and the remote database timestamp.
 * This time stamp tells the app if the local database needs to update and fetch new data.
 * */
class RemoteConfig @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val api: Api,
    private val database: Database,
    private val networkHelper: NetworkHelper
) {


    private val isConnected = networkHelper.isConnectedToInternet()


    /**Public*/
    suspend fun isRemoteConfigOutdated(scope: CoroutineScope): Boolean {

        val localRemoteConfig = getLocalRemoteConfig()
        val newRemoteConfig = fetchNewRemoteConfig(scope)

        return if (isOutdated(localRemoteConfig, newRemoteConfig)) {
            handleAnnouncements(newRemoteConfig)   // Check if there are new announcements
            saveRemoteConfigLocally(newRemoteConfig)  // Save locally
            true
        } else false
    }


    /**Util*/
    private fun isOutdated(localRemoteConfig: String?, newRemoteConfig: String?): Boolean {
        // Check if timestamp on new remoteConfig is more recent than the one saved locally
        return (getLastUpdated(newRemoteConfig) > getLastUpdated(localRemoteConfig))
    }

    private fun getLocalRemoteConfig(): String? {
        //Return the remote config file that is saved locally
        return sharedPreferences.getString(REMOTE_CONFIG, "")
    }

    private suspend fun fetchNewRemoteConfig(scope: CoroutineScope): String? {
        return if (isConnected) {
            val result = scope.async {
                api.getRemoteConfig().toString()
            }
            result.await()
        } else null
    }

    private suspend fun handleAnnouncements(remoteConfig: String?) {
        database.announcementDao().deleteAnnouncement()
        val announcement = getAnnouncement(remoteConfig)
        if (announcement != null)
            database.announcementDao().insertAnnouncement(announcement)
    }

    private fun saveRemoteConfigLocally(newRemoteConfig: String?) {
        if (newRemoteConfig != null)
            sharedPreferences.edit().putString(REMOTE_CONFIG, newRemoteConfig.toString()).apply()
    }


    /**Parse*/
    private fun getLastUpdated(remoteConfig: String?): Long {
        return if (!remoteConfig.isNullOrEmpty())
            JSONObject(remoteConfig).getLong("last_updated")
        else 0L
    }

    private fun getAnnouncement(remoteConfig: String?): Announcement? {
        return if (!remoteConfig.isNullOrEmpty()) {
            val parentObject = JSONObject(remoteConfig)
            val objAnnouncement: JSONObject = parentObject.getJSONObject("announcement")
            val showAnnouncement: Boolean = parentObject.getBoolean("show_announcement")
            if (showAnnouncement)
                Gson().fromJson(objAnnouncement.toString(), Announcement::class.java)
            else null
        } else null
    }
}