package com.bartuciotti.torontowanted.pages.details.ui.adapter

import android.util.Log
import android.view.View
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.bartuciotti.torontowanted.pages.investigations.data.Suspect
import com.bartuciotti.torontowanted.pages.investigations.data.Victim
import android.view.ViewGroup
import com.bartuciotti.torontowanted.R
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide

/**
 * Adapter for List of Victims/Suspects RecyclerView.
 *
 * OBS: If the details page was more complex (nested recycler views horizontal and vertical etc...),
 * I would use an approach similar to the one used for InvestigationsPage, encapsulating each
 * viewHolder, binder and lists to build into a single Recyclerview.
 * */
class VictimsSuspectsAdapter : RecyclerView.Adapter<VictimsSuspectsAdapter.ViewHolder>() {


    private var victimsList: List<Victim> = listOf()
    private var suspectsList: List<Suspect> = listOf()


    /** Public */
    fun updateVictimsList(victimsList: List<Victim>) {
        if (this.victimsList != victimsList) {
            Log.d(TAG, "updateVictimsList")
            this.victimsList = victimsList
            this.notifyDataSetChanged()
        }
    }

    fun updateSuspectsList(suspectsList: List<Suspect>) {
        if (this.suspectsList != suspectsList) {
            Log.d(TAG, "updateSuspectsList")
            this.suspectsList = suspectsList
            this.notifyDataSetChanged()
        }
    }


    /** Recycler View */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_victim_suspect_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when {
            victimsList.isNotEmpty() -> bindVictim(victimsList[position], holder)
            suspectsList.isNotEmpty() -> bindSuspect(suspectsList[position], holder)
            else -> Log.d(TAG, "No victims or suspects to be displayed.")
        }
    }

    override fun getItemCount(): Int {
        return when {
            victimsList.isNotEmpty() -> victimsList.size
            suspectsList.isNotEmpty() -> suspectsList.size
            else -> 0
        }
    }


    /** View Holder */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.findViewById(R.id.image)
        val nameView: TextView = view.findViewById(R.id.tvName)
        val genderView: TextView = view.findViewById(R.id.tvGender)
        val ageView: TextView = view.findViewById(R.id.tvAge)
        val divisionView: TextView = view.findViewById(R.id.tvDivision)
        val dateView: TextView = view.findViewById(R.id.tvDate)
        val containerVictimInfo: ConstraintLayout = view.findViewById(R.id.containerVictimInfo)
    }


    /** Bind Helpers */
    private fun bindVictim(victim: Victim, holder: ViewHolder) {

        Glide.with(holder.imageView)
            .load(victim.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.imageView)

        holder.nameView.text = victim.name
        holder.genderView.text = victim.gender
        holder.ageView.text = victim.age
        holder.divisionView.text = victim.division
        holder.dateView.text = victim.date
    }

    private fun bindSuspect(suspect: Suspect, holder: ViewHolder) {
        Glide.with(holder.imageView)
            .load(suspect.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.imageView)

        holder.nameView.text = suspect.name
        holder.ageView.text = suspect.age
        holder.containerVictimInfo.visibility = View.INVISIBLE
    }

    private val TAG = VictimsSuspectsAdapter::class.simpleName
}