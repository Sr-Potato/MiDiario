package com.midiario.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.midiario.data.model.EntryModel
import java.util.concurrent.Flow

@Dao
interface DiaryEntryDao {

    @Query("SELECT * FROM diary_entries WHERE id = :id")
    suspend fun getEntryById(id: Int): EntryModel?

    @Query("SELECT * FROM diary_entries WHERE userEmail = :email ORDER BY createdAt DESC")
    fun getEntriesByEmail(email: String): LiveData<List<EntryModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: EntryModel):Long

    @Update
    suspend fun updateEntry(entry: EntryModel)

    @Delete
    suspend fun deleteEntry(entry:EntryModel)

}
