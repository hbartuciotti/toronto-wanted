package com.bartuciotti.torontowanted.pages.investigations.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsolvedCase(
    var victimId: Int,
    var caseId: Int,
    var name: String,
    var age: String,
    var gender: String,
    var division: String,
    var date: String,
    var image: String?,
    var caseName: String,
    var subtitle: String,
    var details: String
) : Parcelable