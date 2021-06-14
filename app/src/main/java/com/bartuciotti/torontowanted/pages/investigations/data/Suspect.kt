package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suspects")
data class Suspect(
    @PrimaryKey
    @ColumnInfo(name = "suspectId")
    var id: Int,
    var caseId: Int,
    var name: String,
    var age: String?,
    var image: String?
)

