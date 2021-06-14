package com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.data.UnsolvedCase
import com.bartuciotti.torontowanted.pages.investigations.data.WantedCase
import com.bartuciotti.torontowanted.pages.investigations.ui.adapter.UnsolvedAdapter
import com.bartuciotti.torontowanted.pages.investigations.ui.adapter.WantedAdapter
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.InvestigationListViewHolder
import com.bartuciotti.torontowanted.util.BaseViewHolder

/**
 * Binds data to the unsolved list - recyclerview
 * */
class UnsolvedListBinder(
    private val unsolvedBindersList: List<UnsolvedBinder>
) : InvestigationsBaseBinder,
    UnsolvedBinder.ClickListener {


    override fun bindView(viewHolder: BaseViewHolder) {
        if (viewHolder is InvestigationListViewHolder) {

            viewHolder.title.text =
                viewHolder.title.context.resources.getString(R.string.unsolved_label)

            setListAdapter(viewHolder.gridRecyclerview)
        }
    }

    private fun setListAdapter(gridRecyclerview: RecyclerView) {

        //Set click Listener for each item
        unsolvedBindersList.forEach { it.setListener(this) }

        gridRecyclerview.adapter = UnsolvedAdapter(unsolvedBindersList)
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
        fun onUnsolvedClicked(unsolvedCase: UnsolvedCase)
    }

    override fun onUnsolvedClicked(unsolvedCase: UnsolvedCase) {
        listener.onUnsolvedClicked(unsolvedCase)
    }
}