package com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.data.WantedCase
import com.bartuciotti.torontowanted.pages.investigations.ui.adapter.WantedAdapter
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.InvestigationListViewHolder
import com.bartuciotti.torontowanted.util.BaseViewHolder

/**
 * Binds data to the wanted list - recyclerview
 * */
class WantedListBinder(
    private val wantedBindersList: List<WantedBinder>
) : InvestigationsBaseBinder,
    WantedBinder.ClickListener {


    override fun bindView(viewHolder: BaseViewHolder) {

        if (viewHolder is InvestigationListViewHolder) {

            viewHolder.title.text =
                viewHolder.title.context.resources.getString(R.string.wanted_label)

            setListAdapter(viewHolder.gridRecyclerview)
        }
    }

    private fun setListAdapter(gridRecyclerview: RecyclerView) {

        //Set click Listener for each item
        wantedBindersList.forEach { it.setListener(this) }

        gridRecyclerview.adapter = WantedAdapter(wantedBindersList)
        gridRecyclerview.layoutManager = GridLayoutManager(gridRecyclerview.context, 2)
    }


    /**
     * Click Listeners
     * */
    private lateinit var listener: ClickListener

    fun setListener(clickListener: ClickListener) {
        listener = clickListener
    }

    interface ClickListener {
        fun onWantedClicked(wantedCase: WantedCase)
    }

    override fun onWantedClicked(wantedCase: WantedCase) {
        listener.onWantedClicked(wantedCase)
    }
}