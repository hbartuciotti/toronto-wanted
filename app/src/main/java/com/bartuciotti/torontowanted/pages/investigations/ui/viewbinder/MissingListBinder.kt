package com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.data.Missing
import com.bartuciotti.torontowanted.pages.investigations.ui.adapter.MissingAdapter
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.InvestigationListViewHolder
import com.bartuciotti.torontowanted.util.BaseViewHolder

/**
 * Binds data to the missing list - recyclerview
 * */
class MissingListBinder(
    private val missingBinderList: List<MissingBinder>
) : InvestigationsBaseBinder,
    MissingBinder.ClickListener {


    override fun bindView(viewHolder: BaseViewHolder) {
        if (viewHolder is InvestigationListViewHolder) {

            viewHolder.title.text =
                viewHolder.title.context.resources.getString(R.string.missing_label)

            setListAdapter(viewHolder.gridRecyclerview)
        }
    }

    private fun setListAdapter(gridRecyclerview: RecyclerView) {

        //Set click Listener for each item
        missingBinderList.forEach { it.setListener(this) }

        gridRecyclerview.adapter = MissingAdapter(missingBinderList)
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
        fun onMissingClicked(missing: Missing)
    }


    override fun onMissingClicked(missing: Missing) {
        listener.onMissingClicked(missing)
    }
}