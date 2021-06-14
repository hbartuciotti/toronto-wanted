package com.bartuciotti.torontowanted.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Used to show SnackBar messages
 * */
object SnackbarHelper {

    fun show(container: View, message: String, duration: Int) {
        val snackBar = Snackbar.make(container, message, duration)

        if (duration == Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction("Dismiss") { dismissSnackBar(snackBar) }

        snackBar.show()
    }

    private fun dismissSnackBar(snackBar: Snackbar?) {
        if (snackBar != null && snackBar.isShown)
            snackBar.dismiss()
    }
}