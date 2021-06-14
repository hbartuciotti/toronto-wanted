package com.bartuciotti.torontowanted.data

import com.bartuciotti.torontowanted.util.Constants
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Api calls.
 * */
interface Api {

    @GET(Constants.URL_REMOTE_CONFIG)
    suspend fun getRemoteConfig(): JsonObject

    @GET(Constants.URL_CASES)
    suspend fun getCases(): ApiResponses.CasesResponse

    @GET(Constants.URL_WANTED)
    suspend fun getWanted(): ApiResponses.WantedResponse

    @GET(Constants.URL_VICTIMS)
    suspend fun getVictims(): ApiResponses.VictimsResponse

    @GET(Constants.URL_SUSPECTS)
    suspend fun getSuspects(): ApiResponses.SuspectsResponse

    @GET(Constants.URL_MISSING)
    suspend fun getMissing(): ApiResponses.MissingResponse

    @GET(Constants.URL_NEWS)
    suspend fun getNews(): ApiResponses.NewsResponse

    @GET(Constants.URL_WANTEDCASE_BY_ID)
    suspend fun getWantedCaseById(@Query("wantedId") wantedId: Int): ApiResponses.WantedCaseByIdResponse

    @GET(Constants.URL_UNSOLVEDCASE_BY_ID)
    suspend fun getUnsolvedCaseById(@Query("victimId") victimId: Int): ApiResponses.UnsolvedCaseByIdResponse

    @GET(Constants.URL_MISSING_BY_ID)
    suspend fun getMissingById(@Query("id") missingId: Int): ApiResponses.MissingByIdResponse
}