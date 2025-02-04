package com.midiario.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_entries")
data class EntryModel(
    @PrimaryKey(autoGenerate = true)val id: Int = 0,
    val title: String,
    val content: String,
    val userEmail: String,
    val createdAt : Long = System.currentTimeMillis()
)