package com.bartuciotti.torontowanted.util

import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


/**
 * Class responsible for logging events in the app.
 * */
object AnalyticsHelper {


    fun pageLoaded(pageName: String) {
        Log.d(TAG, "Firebase Analytics - pageLoaded - $pageName")
        logToFirebaseAnalytics(Constants.ANALYTICS_PAGE_LOADED, pageName)
    }

    fun investigationSelected(type: String, id: String, content: String) {
        Log.d(TAG, "Firebase Analytics - investigationSelected - $id")
        logToFirebaseAnalytics(Constants.ANALYTICS_INVESTIGATION_SELECTED, type, id, content)
    }

    fun videoOpened(wantedCaseId: String, videoUrl: String) {
        Log.d(TAG, "Firebase Analytics - videoOpened - $videoUrl")
        logToFirebaseAnalytics(Constants.ANALYTICS_VIDEO_OPENED, Constants.INVESTIGATION_WANTED, wantedCaseId, videoUrl)
    }

    fun newsSelected(position: Int, date: String) {
        Log.d(TAG, "Firebase Analytics - newsSelected - $position")
        logToFirebaseAnalytics(Constants.ANALYTICS_NEWS_OPENED, "$position - $date")
    }

    fun rateUsClicked() {
        Log.d(TAG, "Firebase Analytics - rateUsClicked")
        logToFirebaseAnalytics(Constants.ANALYTICS_RATE_US_CLICKED, "rateus")
    }

    fun announcementClicked() {
        Log.d(TAG, "Firebase Analytics - announcementClicked")
        logToFirebaseAnalytics(Constants.ANALYTICS_ANNOUNCEMENT_CLICKED, "announcement")
    }


    /** Private */
    private fun logToFirebaseAnalytics(
        eventName: String,
        itemCategory: String,
        itemId: String,
        content: String
    ) {
        Firebase.analytics.logEvent(eventName) {
            param(FirebaseAnalytics.Param.ITEM_CATEGORY, itemCategory)
            param(FirebaseAnalytics.Param.ITEM_ID, itemId)
            param(FirebaseAnalytics.Param.CONTENT, content)
        }
    }

    private fun logToFirebaseAnalytics(eventName: String, content: String) {
        Firebase.analytics.logEvent(eventName) {
            param(FirebaseAnalytics.Param.CONTENT, content)
        }
    }

    private val TAG = AnalyticsHelper::class.simpleName as String
}