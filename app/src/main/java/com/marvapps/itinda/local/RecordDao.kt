package com.marvapps.itinda.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.marvapps.itinda.model.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Insert
    suspend fun insert(record: Record)

    @Query("SELECT * FROM record")
    fun getRecords(): Flow<List<Record>>

    @Query("DELETE FROM record WHERE id = :id")
    suspend fun deleteRecord(id: Int)


}