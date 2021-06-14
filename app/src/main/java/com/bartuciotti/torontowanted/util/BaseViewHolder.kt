package com.bartuciotti.torontowanted.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Super class to assist single recyclerview approach.
 * Every ViewHolder extends this class
 * */
abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onUnbind()
}