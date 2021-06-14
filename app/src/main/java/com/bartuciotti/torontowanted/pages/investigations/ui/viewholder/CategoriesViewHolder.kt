package com.bartuciotti.torontowanted.pages.investigations.ui.viewholder

import android.view.View
import android.widget.TextView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.util.BaseViewHolder
import com.google.android.material.chip.Chip

/**
 * Represents the categories view
 * */
class CategoriesViewHolder(itemView: View) : BaseViewHolder(itemView) {

    //item_categories.xml
    val title: TextView = itemView.findViewById(R.id.tvTitle)
    val chipWanted: Chip = itemView.findViewById(R.id.chipWanted)
    val chipUnsolved: Chip = itemView.findViewById(R.id.chipUnsolved)
    val chipMissing: Chip = itemView.findViewById(R.id.chipMissing)

    override fun onUnbind() {}
}