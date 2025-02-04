package com.midiario.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.midiario.data.dao.DiaryEntryDao
import com.midiario.data.model.EntryModel
import com.midiario.navigation.Nav
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DiaryRepository(private val dao: DiaryEntryDao) {

    fun getEntriesByEmail(email: String): StateFlow<List<EntryModel>> {
        val liveData = dao.getEntriesByEmail(email)
        return liveData.asFlow().stateIn(
            scope = CoroutineScope(Dispatchers.Main),
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
    }

    suspend fun insertEntry(entry: EntryModel) {

        dao.insertEntry(entry)
        Log.e("Yupiii", "Estas introduciendo una entrada nueva desde DiaryRepository")
    }

    suspend fun updateEntry(entry: EntryModel) = dao.updateEntry(entry)
    suspend fun getEntryById(id: Int): EntryModel? {
        return dao.getEntryById(id)
    }

    suspend fun deleteEntry(entry: EntryModel) = dao.deleteEntry(entry)
}