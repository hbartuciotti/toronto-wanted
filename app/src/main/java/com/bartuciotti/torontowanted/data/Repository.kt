package com.bartuciotti.torontowanted.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.bartuciotti.torontowanted.pages.investigations.data.*
import com.bartuciotti.torontowanted.pages.news.data.News
import com.bartuciotti.torontowanted.util.NetworkHelper
import com.bartuciotti.torontowanted.util.RemoteConfig
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * Main Repository. Responsible for:
 * - Fetching data from API
 * - Updating local Database
 * - Getting data from local Database
 * OBS: For more complex database schemas, is recommended to have multiple repository classes. One for each type of data.
 * */
class Repository @Inject constructor(
    private val database: Database,
    private val api: Api,
    private val remoteConfig: RemoteConfig,
    private val networkHelper: NetworkHelper
) {


    private val isConnected = networkHelper.isConnectedToInternet()


    fun syncDatabase() {
        GlobalScope.launch(Dispatchers.IO) {
            if (isTablesEmpty() || remoteConfig.isRemoteConfigOutdated(this)) {
                Log.d(TAG, "syncDatabase")
                fetchCases()
                fetchWanted()
                fetchVictims()
                fetchMissing()
                fetchSuspects()
                fetchNews()
            }
        }
    }


    /** Fetch */
    private suspend fun fetchCases() {
        Log.d(TAG, "fetchCases")
        if (isConnected) {
            val response = api.getCases()
            val casesList: List<Case> = response.data
            insertCasesToDatabase(casesList)
        }
    }

    private suspend fun fetchWanted() {
        Log.d(TAG, "fetchWanted")
        if (isConnected) {
            val response = api.getWanted()
            val wantedList: List<Wanted> = response.data
            insertWantedToDatabase(wantedList)
        }
    }

    private suspend fun fetchVictims() {
        Log.d(TAG, "fetchVictims")
        if (isConnected) {
            val response = api.getVictims()
            val victimsList: List<Victim> = response.data
            insertVictimsToDatabase(victimsList)
        }
    }

    private suspend fun fetchMissing() {
        Log.d(TAG, "fetchMissing")
        if (isConnected) {
            val response = api.getMissing()
            val missingList: List<Missing> = response.data
            insertMissingToDatabase(missingList)
        }
    }

    private suspend fun fetchSuspects() {
        Log.d(TAG, "fetchSuspects")
        if (isConnected) {
            val response = api.getSuspects()
            val suspectsList: List<Suspect> = response.data
            insertSuspectsToDatabase(suspectsList)
        }
    }

    private suspend fun fetchNews() {
        Log.d(TAG, "fetchMissing")
        if (isConnected) {
            val response = api.getNews()
            val news: List<News> = response.data
            insertNewsToDatabase(news)
        }
    }

    private suspend fun fetchWantedCaseById(wantedId: Int): WantedCase? {
        Log.d(TAG, "fetchWantedCaseById")
        return if (isConnected) {
            val response = api.getWantedCaseById(wantedId)
            response.wantedCase
        } else null
    }

    private suspend fun fetchUnsolvedCaseById(victimId: Int): UnsolvedCase? {
        Log.d(TAG, "fetchUnsolvedCaseById")
        return if (isConnected) {
            val response = api.getUnsolvedCaseById(victimId)
            response.unsolvedCase
        } else null
    }

    private suspend fun fetchMissingById(missingId: Int): Missing? {
        Log.d(TAG, "fetchMissingById")
        return if (isConnected) {
            val response = api.getMissingById(missingId)
            response.missing
        } else null
    }


    /** Room Inserts */
    private suspend fun insertCasesToDatabase(list: List<Case>?) {
        if (!list.isNullOrEmpty())
            database.caseDao().insertCases(list)
    }

    private suspend fun insertWantedToDatabase(list: List<Wanted>?) {
        if (!list.isNullOrEmpty())
            database.wantedDao().insertWantedCriminals(list)
    }

    private suspend fun insertVictimsToDatabase(list: List<Victim>?) {
        if (!list.isNullOrEmpty())
            database.victimDao().insertVictims(list)
    }

    private suspend fun insertMissingToDatabase(list: List<Missing>?) {
        if (!list.isNullOrEmpty())
            database.missingDao().insertMissing(list)
    }

    private suspend fun insertSuspectsToDatabase(list: List<Suspect>?) {
        if (!list.isNullOrEmpty())
            database.suspectDao().insertSuspects(list)
    }

    private suspend fun insertNewsToDatabase(list: List<News>?) {
        if (!list.isNullOrEmpty())
            database.newsDao().insertNews(list)
    }


    /** Gets */
    fun getWantedCases(): LiveData<List<WantedCase>> {
        Log.d(TAG, "getWantedCases")
        return database.wantedDao().getWantedAndCaseData()
    }

    fun getUnsolvedCases(): LiveData<List<UnsolvedCase>> {
        Log.d(TAG, "getUnsolvedCases")
        return database.victimDao().getVictimAndCaseData()
    }

    fun getMissing(): LiveData<List<Missing>> {
        Log.d(TAG, "getMissing")
        return database.missingDao().getMissing()
    }

    fun getNews(): LiveData<List<News>> {
        Log.d(TAG, "getNews")
        return database.newsDao().getNews()
    }

    fun getAnnouncement(): LiveData<Announcement> {
        Log.d(TAG, "getAnnouncement")
        return database.announcementDao().getAnnouncement()
    }

    suspend fun getVictimsForCase(caseId: Int): List<Victim> {
        return database.victimDao().getVictimsForCase(caseId)
    }

    suspend fun getSuspectsForCase(caseId: Int): List<Suspect> {
        return database.suspectDao().getSuspectsForCase(caseId)
    }

    suspend fun getWantedCaseById(wantedId: Int): WantedCase? {
        var wantedCase = database.wantedDao().getWantedCaseId(wantedId)

        if (wantedCase == null) // If not in database, fetch object from Api
            wantedCase = fetchWantedCaseById(wantedId)

        return wantedCase
    }

    suspend fun getUnsolvedCaseById(victimId: Int): UnsolvedCase? {
        var unsolvedCase = database.victimDao().getUnsolvedCaseId(victimId)

        if (unsolvedCase == null) // If not in database, fetch object from Api
            unsolvedCase = fetchUnsolvedCaseById(victimId)

        return unsolvedCase
    }

    suspend fun getMissingById(missingId: Int): Missing? {
        var missing = database.missingDao().getMissingById(missingId)

        if (missing == null) // If not in database, fetch object from Api
            missing = fetchMissingById(missingId)

        return missing
    }


    /**Util*/
    private suspend fun isTablesEmpty(): Boolean {
        return database.caseDao().getCountCasesTable() == 0
                || database.wantedDao().getCountWantedTable() == 0
                || database.victimDao().getCountVictimsTable() == 0
                || database.missingDao().getCountMissingTable() == 0
                || database.newsDao().getCountNewsTable() == 0
    }

    private val TAG = Repository::class.simpleName
}