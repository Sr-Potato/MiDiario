package com.midiario.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.midiario.data.model.EntryModel
import com.midiario.data.repository.DiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EntryViewModel(private val dr: DiaryRepository) : ViewModel() {

    private val _entries = MutableStateFlow<List<EntryModel>>(emptyList())
    val entries: StateFlow<List<EntryModel>> get() = _entries

    private val _entry = MutableStateFlow<EntryModel?>(null)
    val entry: StateFlow<EntryModel?> get() = _entry

    fun insertEntry(title: String, content: String, email: String) {
        if (email.isBlank()) {
            Log.e("EntryViewModel", "Email is required to save an entry.")
            return
        }
        viewModelScope.launch {
            val newEntry = EntryModel(title = title, content = content, userEmail = email)
            dr.insertEntry(newEntry)
            loadEntries(email)
        }
    }

    fun deleteEntry(entry: EntryModel, onDeleted: () -> Unit) {
        viewModelScope.launch {
            dr.deleteEntry(entry)
            onDeleted()
        }
    }

    fun loadEntries(email: String) {
        viewModelScope.launch {
            val entriesList = dr.getEntriesByEmail(email).value.orEmpty()
            _entries.value = entriesList
        }
    }

    fun updateEntry(entry: EntryModel) {
        viewModelScope.launch {
            dr.updateEntry(entry)
            _entry.value = dr.getEntryById(entry.id)
        }
    }

    fun getEntryById(entryId: Int) {
        viewModelScope.launch {
            _entry.value = dr.getEntryById(entryId)
        }
    }
}