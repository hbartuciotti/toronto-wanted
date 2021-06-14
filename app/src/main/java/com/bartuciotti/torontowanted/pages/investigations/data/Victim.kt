package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.room.*

@Entity(tableName = "victims")
data class Victim(
    @PrimaryKey
    @ColumnInfo(name = "victimId")
    var id: Int,
    var caseId: Int,
    var name: String,
    var age: String,
    var gender: String,
    var division: String,
    var date: String,
    var image: String?,
)



