package com.bartuciotti.torontowanted.pages.investigations.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.util.BaseViewHolder

/**
 * Represents the announcement view
 * */
class AnnouncementViewHolder(itemView: View) : BaseViewHolder(itemView) {

    //item_announcement.xml
    val container: ConstraintLayout = itemView.findViewById(R.id.container)
    val image: ImageView = itemView.findViewById(R.id.image)
    val title: TextView = itemView.findViewById(R.id.tvTitle)
    val subtitle: TextView = itemView.findViewById(R.id.tvSubtitle)
    val date: TextView = itemView.findViewById(R.id.tvDate)
    val link: TextView = itemView.findViewById(R.id.tvLink)

    override fun onUnbind() {
        image.setImageDrawable(null)
    }
}