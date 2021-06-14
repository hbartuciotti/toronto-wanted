package com.bartuciotti.torontowanted.pages.investigations.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MissingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissing(wantedCriminals: List<Missing>)

    @Query("SELECT * FROM missing ORDER BY missing.id DESC")
    fun getMissing(): LiveData<List<Missing>>

    @Transaction
    @Query("SELECT * FROM missing WHERE missing.id = :missingId")
    suspend fun getMissingById(missingId: Int): Missing?

    @Query("SELECT count(*) FROM missing")
    suspend fun getCountMissingTable(): Int
}