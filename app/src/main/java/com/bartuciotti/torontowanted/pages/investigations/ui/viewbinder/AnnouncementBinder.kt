package com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder

import android.view.View
import android.widget.ImageView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.data.Announcement
import com.bartuciotti.torontowanted.pages.investigations.data.UnsolvedCase
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.AnnouncementViewHolder
import com.bartuciotti.torontowanted.util.BaseViewHolder
import com.bumptech.glide.Glide

/**
 * Binds data to the announcement view
 * */
class AnnouncementBinder(
    private val announcement: Announcement
) : InvestigationsBaseBinder {


    override fun bindView(viewHolder: BaseViewHolder) {

        if (viewHolder is AnnouncementViewHolder) {

            viewHolder.title.text = announcement.title
            viewHolder.subtitle.text = announcement.subtitle
            viewHolder.date.text = announcement.date
            viewHolder.link.text = announcement.link

            //Image
            if (!announcement.image.isNullOrEmpty())
                bindImage(viewHolder.image)
            else
                viewHolder.image.visibility = View.GONE

            //Click Listeners
            viewHolder.container.setOnClickListener {
                listener.onAnnouncementClicked(announcement)
            }
        }
    }

    private fun bindImage(image: ImageView) {
        image.visibility = View.VISIBLE
        Glide.with(image.context)
            .load(announcement.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(image)
    }


    /**
     * Click Listeners
     * */
    private lateinit var listener: ClickListener

    fun setListener(clickListener: ClickListener) {
        listener = clickListener
    }

    interface ClickListener {
        fun onAnnouncementClicked(announcement: Announcement)
    }
}