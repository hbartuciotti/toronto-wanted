package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents the announcement that can be displayed on the top section of the investigations page.
 * */
@Entity(tableName = "announcement")
data class Announcement(
    var image: String? = "",
    var title: String = "",
    var subtitle: String = "",
    var date: String = "",
    var link: String? = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}