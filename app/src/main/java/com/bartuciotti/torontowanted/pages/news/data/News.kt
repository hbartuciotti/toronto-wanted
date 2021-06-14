package com.bartuciotti.torontowanted.pages.news.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    var id: Int,
    var date: String,
    var title: String,
    var link: String
)

