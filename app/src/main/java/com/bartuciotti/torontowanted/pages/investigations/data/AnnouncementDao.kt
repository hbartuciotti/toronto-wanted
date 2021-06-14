package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnnouncementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(announcement: Announcement)

    @Query("DELETE FROM announcement")
    suspend fun deleteAnnouncement()

    @Query("SELECT * FROM announcement LIMIT 1")
    fun getAnnouncement(): LiveData<Announcement>
}