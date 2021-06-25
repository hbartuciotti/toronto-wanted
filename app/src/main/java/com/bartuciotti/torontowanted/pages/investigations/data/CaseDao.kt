package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.room.*

@Dao
interface CaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCases(suspects: List<Case>)

    @Query("DELETE FROM cases")
    suspend fun deleteCases()

    @Query("SELECT count(*) FROM cases")
    suspend fun getCountCasesTable(): Int
}