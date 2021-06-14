package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cases")
data class Case(
    @PrimaryKey
    var id: Int,
    var caseName: String,
    var subtitle: String,
    var details: String,
    var isUnsolved: Int
)
