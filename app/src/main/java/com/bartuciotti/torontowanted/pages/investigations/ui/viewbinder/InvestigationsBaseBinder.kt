package com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder

import com.bartuciotti.torontowanted.util.BaseViewHolder

/**
 * All view binders for the Investigations page extend this interface
 * */
interface InvestigationsBaseBinder {

    //Function used to bind the data received by the ViewBinder class to correspondent view holder
    fun bindView(viewHolder: BaseViewHolder)

    companion object {
        //IDs for each view type that can be added to the recycler view
        const val ANNOUNCEMENT = 0
        const val CATEGORIES = 1
        const val WANTED = 2
        const val UNSOLVED = 3
        const val MISSING = 4
    }
}