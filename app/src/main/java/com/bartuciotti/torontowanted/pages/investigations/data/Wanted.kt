package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.room.*

@Entity(tableName = "wanted")
data class Wanted(
    @PrimaryKey
    @ColumnInfo(name = "wantedId")
    var id: Int,
    var caseId: Int,
    var name: String,
    var charge: String,
    var image: String?,
    var video: String?
)


