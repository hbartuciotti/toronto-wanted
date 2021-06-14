package com.bartuciotti.torontowanted.pages.investigations.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.util.BaseViewHolder

/**
 * Represents the list view for investigations (wanted, unsolved, missing)
 * */
class InvestigationListViewHolder(itemView: View) : BaseViewHolder(itemView) {

    //item_investigation_list.xml
    val title: TextView = itemView.findViewById(R.id.tvTitle)
    val gridRecyclerview: RecyclerView = itemView.findViewById(R.id.recyclerview)

    override fun onUnbind() {}
}