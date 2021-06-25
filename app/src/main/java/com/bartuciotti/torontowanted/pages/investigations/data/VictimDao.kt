package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VictimDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVictims(wantedCriminals: List<Victim>)

    @Query("DELETE FROM victims")
    suspend fun deleteVictims()

    @Transaction
    @Query("SELECT victims.*, cases.* FROM victims INNER JOIN cases on victims.caseId = cases.id WHERE cases.isUnsolved = 1 ORDER BY victims.victimId DESC")
    fun getVictimAndCaseData(): LiveData<List<UnsolvedCase>>

    @Transaction
    @Query("SELECT victims.* FROM victims INNER JOIN cases ON victims.caseId = cases.id WHERE victims.caseId = :caseId")
    suspend fun getVictimsForCase(caseId: Int): List<Victim>

    @Transaction
    @Query("SELECT victims.*, cases.* FROM victims INNER JOIN cases on cases.id = victims.caseId WHERE victims.victimId = :victimId")
    suspend fun getUnsolvedCaseId(victimId: Int): UnsolvedCase?

    @Query("SELECT count(*) FROM victims")
    suspend fun getCountVictimsTable(): Int
}