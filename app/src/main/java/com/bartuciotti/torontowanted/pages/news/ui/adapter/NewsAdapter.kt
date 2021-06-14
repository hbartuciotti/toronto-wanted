package com.bartuciotti.torontowanted.pages.news.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.news.data.News

/**
 * Adapter for List of News RecyclerView.
 *
 * OBS: If this page was more complex (nested recycler views horizontal and vertical etc...),
 * I would use an approach similar to the one used for InvestigationsPage, encapsulating each
 * viewHolder, binder and lists to build into a single Recyclerview.
 * */
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    private var newsList: List<News> = listOf()


    fun updateData(newsList: List<News>) {
        if (this.newsList != newsList) {
            Log.d(TAG, "updateData")
            this.newsList = newsList
            this.notifyDataSetChanged()
        }
    }


    /** Recycler View */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindNews(newsList[position], holder)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }


    /** ViewHolder */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.tvDate)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val container: ConstraintLayout = view.findViewById(R.id.container)
    }


    /** View Binder */
    private fun bindNews(news: News, holder: ViewHolder) {

        holder.date.text = news.date
        holder.title.text = news.title

        //Click Listener
        holder.container.setOnClickListener {
            listener.onNewsClicked(news)
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
        fun onNewsClicked(news: News)
    }

    private val TAG = NewsAdapter::class.simpleName
}