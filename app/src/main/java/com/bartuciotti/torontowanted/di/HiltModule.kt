package com.bartuciotti.torontowanted.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bartuciotti.torontowanted.data.Database
import com.bartuciotti.torontowanted.data.Repository
import com.bartuciotti.torontowanted.data.Api
import com.bartuciotti.torontowanted.util.Constants
import com.bartuciotti.torontowanted.util.NetworkHelper
import com.bartuciotti.torontowanted.util.RemoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dependency Injection
 * Hilt Module. Responsible for providing dependencies across de application.
 * */
@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideRepository(
        database: Database,
        api: Api,
        remoteConfig: RemoteConfig,
        networkHelper: NetworkHelper
    ): Repository {
        return Repository(database, api, remoteConfig, networkHelper)
    }

    @Provides
    fun provideNetworkHelper(@ApplicationContext appContext: Context): NetworkHelper {
        return NetworkHelper(appContext)
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideRemoteConfig(
        api: Api,
        sharedPreferences: SharedPreferences,
        database: Database,
        networkHelper: NetworkHelper
    ): RemoteConfig {
        return RemoteConfig(sharedPreferences, api, database, networkHelper)
    }
}