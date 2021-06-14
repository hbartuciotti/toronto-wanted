package com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder

import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.data.WantedCase
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.InvestigationViewHolder
import com.bartuciotti.torontowanted.util.BaseViewHolder
import com.bumptech.glide.Glide

/**
 * Binds data to the wanted item view
 * */
class WantedBinder(private val wantedCase: WantedCase) : InvestigationsBaseBinder {


    override fun bindView(viewHolder: BaseViewHolder) {

        if (viewHolder is InvestigationViewHolder) {

            viewHolder.name.text = wantedCase.name
            viewHolder.subtitle.text = wantedCase.subtitle

            // Image
            Glide.with(viewHolder.image)
                .load(wantedCase.image)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(viewHolder.image)

            //Click Listener
            viewHolder.container.setOnClickListener {
                listener.onWantedClicked(wantedCase)
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
        fun onWantedClicked(wantedCase: WantedCase)
    }
}