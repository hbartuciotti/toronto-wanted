package com.bartuciotti.torontowanted.pages.investigations.ui.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.util.BaseViewHolder

/**
 * Represents the investigation item card view
 * */
class InvestigationViewHolder(itemView: View) : BaseViewHolder(itemView) {

    //item_investigation.xml
    val container: CardView = itemView.findViewById(R.id.container)
    val image: ImageView = itemView.findViewById(R.id.image)
    val name: TextView = itemView.findViewById(R.id.tvName)
    val subtitle: TextView = itemView.findViewById(R.id.tvSubtitle)


    override fun onUnbind() {
        image.setImageDrawable(null)
    }
}