package com.bartuciotti.torontowanted.pages.investigations.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.WantedBinder
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.InvestigationViewHolder

/**
 * Adapter for the RecyclerView for wanted cases (List<WantedBinder>)
 * */
class WantedAdapter(private val wantedList: List<WantedBinder>) :
    RecyclerView.Adapter<InvestigationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvestigationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_investigation, parent, false)
        return InvestigationViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvestigationViewHolder, position: Int) {
        wantedList[position].bindView(holder)
    }

    override fun getItemCount(): Int {
        return wantedList.size
    }

    override fun onViewRecycled(holder: InvestigationViewHolder) {
        holder.onUnbind()
        super.onViewRecycled(holder)
    }
}