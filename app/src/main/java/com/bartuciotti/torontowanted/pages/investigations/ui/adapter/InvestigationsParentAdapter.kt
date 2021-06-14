package com.bartuciotti.torontowanted.pages.investigations.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.*
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.ANNOUNCEMENT
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.CATEGORIES
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.MISSING
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.UNSOLVED
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.InvestigationsBaseBinder.Companion.WANTED
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.AnnouncementViewHolder
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.CategoriesViewHolder
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.InvestigationListViewHolder
import com.bartuciotti.torontowanted.util.BaseViewHolder

/**
 * Adapter for the parent RecyclerView.
 * This adapter will receive a list of viewTypes and already binded views(InvestigationsBaseBinder),
 * and assign viewHolders and click listeners for those views.
 *
 * This allows me to add multiple types of views(ex: multiple vertical and horizontal lists) and make
 * sure the view are being recycled.
 * */
class InvestigationsParentAdapter : RecyclerView.Adapter<BaseViewHolder>() {


    private var elementsList: List<Pair<Int, InvestigationsBaseBinder>> = listOf()


    fun updateData(elementsList: List<Pair<Int, InvestigationsBaseBinder>>) {
        if (this.elementsList != elementsList) {
            Log.d(TAG, "updateData")
            this.elementsList = elementsList
            this.notifyDataSetChanged()
        }
    }


    /** Helpers */
    fun getWantedPosition(): Int {
        return elementsList.indexOfFirst { it.first == WANTED }
    }

    fun getUnsolvedPosition(): Int {
        return elementsList.indexOfFirst { it.first == UNSOLVED }
    }

    fun getMissingPosition(): Int {
        return elementsList.indexOfFirst { it.first == MISSING }
    }


    /**
     * Recycler View
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ANNOUNCEMENT -> {
                val view = inflater.inflate(R.layout.item_announcement, parent, false)
                AnnouncementViewHolder(view)
            }
            CATEGORIES -> {
                val view = inflater.inflate(R.layout.item_categories, parent, false)
                CategoriesViewHolder(view)
            }
            WANTED -> {
                val view = inflater.inflate(R.layout.item_investigation_list, parent, false)
                InvestigationListViewHolder(view)
            }
            UNSOLVED -> {
                val view = inflater.inflate(R.layout.item_investigation_list, parent, false)
                InvestigationListViewHolder(view)
            }
            MISSING -> {
                val view = inflater.inflate(R.layout.item_investigation_list, parent, false)
                InvestigationListViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        // Set Click Listeners Before Binding
        when (getItemViewType(position)) {
            ANNOUNCEMENT ->
                (elementsList[position].second as AnnouncementBinder).setListener(announcementListener)

            CATEGORIES ->
                (elementsList[position].second as CategoriesBinder).setListener(categoriesListener)

            WANTED ->
                (elementsList[position].second as WantedListBinder).setListener(wantedListListener)

            UNSOLVED ->
                (elementsList[position].second as UnsolvedListBinder).setListener(unsolvedListListener)

            MISSING ->
                (elementsList[position].second as MissingListBinder).setListener(missingListListener)
        }

        elementsList[position].second.bindView(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return elementsList[position].first
    }

    override fun getItemCount(): Int {
        return elementsList.size
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        holder.onUnbind()
        super.onViewRecycled(holder)
    }


    /** Click Listeners */
    private lateinit var announcementListener: AnnouncementBinder.ClickListener
    fun setAnnouncementListener(clickListener: AnnouncementBinder.ClickListener) {
        announcementListener = clickListener
    }

    private lateinit var categoriesListener: CategoriesBinder.ClickListener
    fun setCategoriesListener(clickListener: CategoriesBinder.ClickListener) {
        categoriesListener = clickListener
    }

    private lateinit var wantedListListener: WantedListBinder.ClickListener
    fun setWantedListListener(clickListener: WantedListBinder.ClickListener) {
        wantedListListener = clickListener
    }

    private lateinit var unsolvedListListener: UnsolvedListBinder.ClickListener
    fun setUnsolvedListListener(clickListener: UnsolvedListBinder.ClickListener) {
        unsolvedListListener = clickListener
    }

    private lateinit var missingListListener: MissingListBinder.ClickListener
    fun setMissingListListener(clickListener: MissingListBinder.ClickListener) {
        missingListListener = clickListener
    }

    private val TAG = InvestigationsParentAdapter::class.simpleName
}