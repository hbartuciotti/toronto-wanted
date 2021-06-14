package com.bartuciotti.torontowanted.pages.investigations.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "missing")
data class Missing(
    @PrimaryKey
    var id: Int,
    var name: String,
    var age: String,
    var gender: String,
    var ethnicity: String,
    var location: String,
    var since: String,
    var details: String,
    var description: String,
    var image: String?,
) : Parcelable