package com.bartuciotti.torontowanted.pages.investigations.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WantedCase(
    var wantedId: Int,
    var caseId: Int,
    var name: String,
    var charge: String,
    var image: String?,
    var video: String?,
    var caseName: String,
    var subtitle: String,
    var details: String
) : Parcelable