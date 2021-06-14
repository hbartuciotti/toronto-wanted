package com.bartuciotti.torontowanted

import android.app.Application
import com.bartuciotti.torontowanted.data.Repository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Entry Point (ApplicationClass > RouteActivity > MainActivity)
 * */
@HiltAndroidApp
class ApplicationClass : Application() {


    @Inject lateinit var repository: Repository


    override fun onCreate() {
        super.onCreate()
        syncDatabase()
    }

    fun syncDatabase(){
            repository.syncDatabase()
    }
}