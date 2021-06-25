package com.bartuciotti.torontowanted.util

import android.content.Intent
import android.util.Log
import androidx.navigation.NavController
import com.bartuciotti.torontowanted.data.Repository
import com.bartuciotti.torontowanted.pages.investigations.data.Missing
import com.bartuciotti.torontowanted.pages.investigations.data.UnsolvedCase
import com.bartuciotti.torontowanted.pages.investigations.data.WantedCase
import com.bartuciotti.torontowanted.pages.investigations.ui.InvestigationsFragmentDirections
import com.bartuciotti.torontowanted.util.Constants.INVESTIGATION_MISSING
import com.bartuciotti.torontowanted.util.Constants.INVESTIGATION_UNSOLVED
import com.bartuciotti.torontowanted.util.Constants.INVESTIGATION_WANTED
import javax.inject.Inject

/**
 * Class responsible for handling deeplinks.
 * */
class DeepLinkHelper @Inject constructor(private val repository: Repository) {


    fun hasDeepLink(intent: Intent): Boolean {
        return (intent.extras != null &&
                intent.extras!!.containsKey(Constants.NOTIFICATION_CATEGORY_KEY) &&
                intent.extras!!.containsKey(Constants.NOTIFICATION_TABLE_ID_KEY))
    }

    fun getDeepLink(intent: Intent): DeepLink {
        val category = intent.extras!!.getString(Constants.NOTIFICATION_CATEGORY_KEY)!!
        val id = intent.extras!!.getString(Constants.NOTIFICATION_TABLE_ID_KEY)!!
        return DeepLink(category, id.toInt())
    }

    suspend fun openDeepLink(deeplink: DeepLink, navController: NavController) {

        when (deeplink.category) {
            INVESTIGATION_WANTED -> {
                val wantedCase = repository.getWantedCaseById(deeplink.itemId)
                if (wantedCase != null)
                    openInvestigationDetailsPage(wantedCase, null, navController)
            }

            INVESTIGATION_UNSOLVED -> {
                val unsolvedCase = repository.getUnsolvedCaseById(deeplink.itemId)
                if (unsolvedCase != null)
                    openInvestigationDetailsPage(null, unsolvedCase, navController)
            }

            INVESTIGATION_MISSING -> {
                val missing = repository.getMissingById(deeplink.itemId)
                if (missing != null)
                    openMissingDetailsPage(missing, navController)
            }

            else -> Log.e(TAG, "Invalid Category")
        }
    }


    /** Util */
    private fun openInvestigationDetailsPage(
        wantedCase: WantedCase? = null,
        unsolvedCase: UnsolvedCase? = null,
        navController: NavController
    ) {
        val destination =
            InvestigationsFragmentDirections.actionFragmentInvestigationsToFragmentDetails(
                wantedCase,
                unsolvedCase
            )
        Log.d(TAG, "openInvestigationDetailsPage")
        navController.navigate(destination)
    }

    private fun openMissingDetailsPage(missing: Missing, navController: NavController) {
        val destination =
            InvestigationsFragmentDirections.actionFragmentInvestigationsToFragmentDetailsMissing(
                missing
            )
        Log.d(TAG, "openMissingDetailsPage")
        navController.navigate(destination)
    }

    private val TAG = DeepLinkHelper::class.simpleName
}