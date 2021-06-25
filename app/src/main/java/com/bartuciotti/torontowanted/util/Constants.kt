package com.bartuciotti.torontowanted.util

/**
 * Constant variables used across he app.
 * */
object Constants {

    // API
    const val BASE_URL = "PRIVATE"

    const val URL_REMOTE_CONFIG = "remote_config.json"
    const val URL_CASES = "cases"
    const val URL_VICTIMS = "victims"
    const val URL_WANTED = "wanted"
    const val URL_SUSPECTS = "suspects"
    const val URL_MISSING = "missing"
    const val URL_NEWS = "news"
    const val URL_WANTEDCASE_BY_ID = "$URL_CASES/wantedCase"
    const val URL_UNSOLVEDCASE_BY_ID = "$URL_CASES/unsolvedCase"
    const val URL_MISSING_BY_ID = "$URL_MISSING/byId"

    // Notifications (Firebase Cloud Messaging Keys)
    const val NOTIFICATION_CATEGORY_KEY = "torontoWantedCategory"
    const val NOTIFICATION_TABLE_ID_KEY = "torontoWantedTableId"
    const val INVESTIGATION_WANTED = "wanted"
    const val INVESTIGATION_UNSOLVED = "unsolved"
    const val INVESTIGATION_MISSING = "missing"

    // Shared Preferences
    const val SHARED_PREFERENCES = "PRIVATE"
    const val REMOTE_CONFIG = "remote_config"

    // Room
    const val DATABASE_NAME = "toronto_wanted_database"

    // Extras
    const val DEEPLINK_EXTRA = "torontoWantedDeepLink"

    //Analytics
    const val ANALYTICS_PAGE_LOADED = "page_loaded"
    const val ANALYTICS_INVESTIGATION_SELECTED = "investigation_selected"
    const val ANALYTICS_VIDEO_OPENED = "video_opened"
    const val ANALYTICS_NEWS_OPENED = "news_opened"
    const val ANALYTICS_ANNOUNCEMENT_CLICKED = "announcement_clicked"
    const val ANALYTICS_RATE_US_CLICKED = "rate_us_clicked"
}