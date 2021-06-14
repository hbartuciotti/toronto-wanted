package com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder

import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.data.Missing
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.InvestigationViewHolder
import com.bartuciotti.torontowanted.util.BaseViewHolder
import com.bumptech.glide.Glide

/**
 * Binds data to the missing item view
 * */
class MissingBinder(private val missing: Missing) : InvestigationsBaseBinder {


    override fun bindView(viewHolder: BaseViewHolder) {
        if (viewHolder is InvestigationViewHolder) {

            viewHolder.name.text = missing.name
            viewHolder.subtitle.text = missing.since

            // Image
            Glide.with(viewHolder.image)
                .load(missing.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(viewHolder.image)

            // Click listener
            viewHolder.container.setOnClickListener {
                listener.onMissingClicked(missing)
            }
        }
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
}