package com.bartuciotti.torontowanted.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bartuciotti.torontowanted.pages.investigations.data.*
import com.bartuciotti.torontowanted.pages.news.data.News
import com.bartuciotti.torontowanted.pages.news.data.NewsDao

/**
 * Room Database.
 * */
@Database(
    entities = [
        Case::class, Wanted::class, Victim::class,
        Suspect::class, Announcement::class,
        Missing::class, News::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun caseDao(): CaseDao
    abstract fun wantedDao(): WantedDao
    abstract fun victimDao(): VictimDao
    abstract fun suspectDao(): SuspectDao
    abstract fun announcementDao(): AnnouncementDao
    abstract fun missingDao(): MissingDao
    abstract fun newsDao(): NewsDao
}