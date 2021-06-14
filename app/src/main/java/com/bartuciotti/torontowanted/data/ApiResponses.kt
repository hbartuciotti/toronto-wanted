package com.bartuciotti.torontowanted.data

import com.bartuciotti.torontowanted.pages.investigations.data.*
import com.bartuciotti.torontowanted.pages.news.data.News

/**
 * All possible responses from the API/
 * */
class ApiResponses {

    data class CasesResponse(
        val status: String,
        val message: String,
        val data: List<Case>
    )

    data class WantedResponse(
        val status: String,
        val message: String,
        val data: List<Wanted>
    )

    data class VictimsResponse(
        val status: String,
        val message: String,
        val data: List<Victim>
    )

    data class MissingResponse(
        val status: String,
        val message: String,
        val data: List<Missing>
    )

    data class SuspectsResponse(
        val status: String,
        val message: String,
        val data: List<Suspect>
    )

    data class NewsResponse(
        val status: String,
        val message: String,
        val data: List<News>
    )

    data class WantedCaseByIdResponse(
        val status: String,
        val message: String,
        val wantedCase: WantedCase
    )

    data class UnsolvedCaseByIdResponse(
        val status: String,
        val message: String,
        val unsolvedCase: UnsolvedCase
    )

    data class MissingByIdResponse(
        val status: String,
        val message: String,
        val missing: Missing
    )
}