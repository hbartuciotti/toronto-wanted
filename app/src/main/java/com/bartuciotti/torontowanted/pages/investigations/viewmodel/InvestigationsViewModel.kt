package com.bartuciotti.torontowanted.pages.investigations.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.bartuciotti.torontowanted.data.Repository
import com.bartuciotti.torontowanted.pages.investigations.data.Announcement
import com.bartuciotti.torontowanted.pages.investigations.data.Missing
import com.bartuciotti.torontowanted.pages.investigations.data.UnsolvedCase
import com.bartuciotti.torontowanted.pages.investigations.data.WantedCase
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.*
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.ANNOUNCEMENT
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.CATEGORIES
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.MISSING
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.UNSOLVED
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.WANTED
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * This viewmodel will observe changes in announcements, wantedCases, unsolvedCases and missing,
 * and update the page when that data changes.
 * */
@HiltViewModel
class InvestigationsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    /** Observers */
    private val investigationsPage =
        object : MediatorLiveData<List<Pair<Int, InvestigationsBaseBinder>>>() {

            private var announcement: Announcement? = null
            private var wantedCases: List<WantedCase> = listOf()
            private var unsolvedCases: List<UnsolvedCase> = listOf()
            private var missing: List<Missing> = listOf()

            init {
                addSource(repository.getAnnouncement()) {
                    if (announcement != it) {
                        announcement = it
                        Log.d(TAG, "Announcement Data Changed")
                        updateInvestigationsPage(wantedCases, unsolvedCases, missing, announcement)
                    }
                }

                addSource(repository.getWantedCases()) {
                    if (it.isNotEmpty() && wantedCases != it) {
                        wantedCases = it
                        Log.d(TAG, "WantedCases Data Changed")
                        updateInvestigationsPage(wantedCases, unsolvedCases, missing, announcement)
                    }
                }

                addSource(repository.getUnsolvedCases()) {
                    if (it.isNotEmpty() && unsolvedCases != it) {
                        unsolvedCases = it
                        Log.d(TAG, "UnsolvedCases Data Changed")
                        updateInvestigationsPage(wantedCases, unsolvedCases, missing, announcement)
                    }
                }

                addSource(repository.getMissing()) {
                    if (it.isNotEmpty() && missing != it) {
                        missing = it
                        Log.d(TAG, "Missing Changed")
                        updateInvestigationsPage(wantedCases, unsolvedCases, missing, announcement)
                    }
                }
            }


            private fun updateInvestigationsPage(
                wantedCases: List<WantedCase>,
                unsolvedCases: List<UnsolvedCase>,
                missing: List<Missing>,
                announcement: Announcement?
            ) {
                Log.d(TAG, "updateInvestigationsPage")

                val viewBinders = bindPage(wantedCases, unsolvedCases, missing, announcement)
                postValue(viewBinders) //update investigationsPage
            }
        }


    /** Binding Helpers */
    private fun bindPage(
        wantedCases: List<WantedCase>,
        unsolvedCases: List<UnsolvedCase>,
        missing: List<Missing>,
        announcement: Announcement?
    ): List<Pair<Int, InvestigationsBaseBinder>> {
        Log.d(TAG, "bindPage")

        val binders = mutableListOf<Pair<Int, InvestigationsBaseBinder>>()

        //Announcement
        if (announcement != null)
            binders.add(Pair(ANNOUNCEMENT, AnnouncementBinder(announcement)))

        //Categories
        binders.add(Pair(CATEGORIES, CategoriesBinder()))

        //Investigations
        if (wantedCases.isNotEmpty()) {
            val wantedBinders = bindWantedCases(wantedCases)
            binders.add(Pair(WANTED, WantedListBinder(wantedBinders)))
        }

        if (unsolvedCases.isNotEmpty()) {
            val unsolvedBinders = bindUnsolvedCases(unsolvedCases)
            binders.add(Pair(UNSOLVED, UnsolvedListBinder(unsolvedBinders)))
        }

        if (missing.isNotEmpty()) {
            val missingBinders = bindMissing(missing)
            binders.add(Pair(MISSING, MissingListBinder(missingBinders)))
        }

        return binders
    }

    private fun bindWantedCases(wantedCases: List<WantedCase>): List<WantedBinder> {

        val wantedBinders: MutableList<WantedBinder> = mutableListOf()
        for (case in wantedCases)
            wantedBinders.add(WantedBinder(case))

        return wantedBinders
    }

    private fun bindUnsolvedCases(unsolvedCases: List<UnsolvedCase>): List<UnsolvedBinder> {

        val unsolvedBinders: MutableList<UnsolvedBinder> = mutableListOf()
        for (case in unsolvedCases)
            unsolvedBinders.add(UnsolvedBinder(case))

        return unsolvedBinders
    }

    private fun bindMissing(missing: List<Missing>): List<MissingBinder> {

        val missingBinders: MutableList<MissingBinder> = mutableListOf()
        for (missingPerson in missing)
            missingBinders.add(MissingBinder(missingPerson))

        return missingBinders
    }


    /** Public */
    fun getInvestigationsPage(): LiveData<List<Pair<Int, InvestigationsBaseBinder>>> {
        Log.d(TAG, "getInvestigationsPage")
        return investigationsPage
    }

    private val TAG = InvestigationsViewModel::class.simpleName
}