package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WantedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWantedCriminals(wantedCriminals: List<Wanted>)

    @Transaction
    @Query("SELECT wanted.*, cases.* FROM wanted INNER JOIN cases on wanted.caseId = cases.id ORDER BY wanted.wantedId DESC")
    fun getWantedAndCaseData(): LiveData<List<WantedCase>>

    @Transaction
    @Query("SELECT wanted.*, cases.* FROM wanted INNER JOIN cases on wanted.caseId = cases.id WHERE wanted.wantedId = :wantedId")
    suspend fun getWantedCaseId(wantedId: Int): WantedCase?

    @Query("SELECT count(*) FROM wanted")
    suspend fun getCountWantedTable(): Int
}