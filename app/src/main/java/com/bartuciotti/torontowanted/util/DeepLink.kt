package com.bartuciotti.torontowanted.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Used to open a deeplink. When user opens the app through a notification, this object can be
 * passed to DeepLinkHelper class in order to open the details page.
 * */
@Parcelize
data class DeepLink(
    val category: String,
    val itemId: Int
): Parcelable

