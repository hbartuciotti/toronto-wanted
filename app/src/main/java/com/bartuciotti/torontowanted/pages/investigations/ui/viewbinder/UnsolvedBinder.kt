package com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder

import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.data.UnsolvedCase
import com.bartuciotti.torontowanted.pages.investigations.data.WantedCase
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.InvestigationViewHolder
import com.bartuciotti.torontowanted.util.BaseViewHolder
import com.bumptech.glide.Glide

/**
 * Binds data to the unsolved item view
 * */
class UnsolvedBinder(private val unsolvedCase: UnsolvedCase) : InvestigationsBaseBinder {


    override fun bindView(viewHolder: BaseViewHolder) {

        if (viewHolder is InvestigationViewHolder) {

            viewHolder.name.text = unsolvedCase.name
            viewHolder.subtitle.text = unsolvedCase.subtitle

            // Image
            Glide.with(viewHolder.image)
                .load(unsolvedCase.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(viewHolder.image)

            // Click Listener
            viewHolder.container.setOnClickListener {
                listener.onUnsolvedClicked(unsolvedCase)
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
        fun onUnsolvedClicked(unsolvedCase: UnsolvedCase)
    }
}