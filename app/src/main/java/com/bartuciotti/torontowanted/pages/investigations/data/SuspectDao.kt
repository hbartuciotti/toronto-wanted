package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.room.*

@Dao
interface SuspectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSuspects(suspects: List<Suspect>)

    @Query("DELETE FROM suspects")
    suspend fun deleteSuspects()

    @Transaction
    @Query("SELECT suspects.* FROM suspects INNER JOIN cases ON suspects.caseId = cases.id WHERE suspects.caseId = :caseId")
    suspend fun getSuspectsForCase(caseId: Int): List<Suspect>
}