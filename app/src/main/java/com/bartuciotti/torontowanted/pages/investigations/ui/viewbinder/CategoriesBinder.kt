package com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder

import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.pages.investigations.ui.viewholder.CategoriesViewHolder
import com.bartuciotti.torontowanted.util.BaseViewHolder

/**
 * Binds data to the categories view
 * */
class CategoriesBinder : InvestigationsBaseBinder {


    override fun bindView(viewHolder: BaseViewHolder) {
        if (viewHolder is CategoriesViewHolder) {

            viewHolder.title.text =
                viewHolder.title.context.resources.getString(R.string.categories_label)
            viewHolder.chipWanted.setOnClickListener { listener.onChipClicked(CHIP_WANTED) }
            viewHolder.chipUnsolved.setOnClickListener { listener.onChipClicked(CHIP_UNSOLVED) }
            viewHolder.chipMissing.setOnClickListener { listener.onChipClicked(CHIP_MISSING) }
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
        fun onChipClicked(chipId: Int)
    }

    companion object {
        const val CHIP_WANTED = 0
        const val CHIP_UNSOLVED = 1
        const val CHIP_MISSING = 2
    }
}