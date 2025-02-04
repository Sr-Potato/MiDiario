package com.midiario.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.midiario.data.model.EntryModel
import com.midiario.data.repository.DiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val dr: DiaryRepository, usuarioViewModel: UsuarioViewModel) :
    ViewModel() {

    private val _entries = MutableStateFlow<List<EntryModel>>(emptyList())
    val entries: StateFlow<List<EntryModel>> get() = _entries

    private val _selectedEntry = MutableStateFlow<EntryModel?>(null) // Entrada seleccionada
    val selectedEntry: StateFlow<EntryModel?> get() = _selectedEntry

    init {
        viewModelScope.launch {
            usuarioViewModel.usuarioEmail.collect() { email ->
                if (!email.isNullOrEmpty()) {
                    loadEntries(email)
                }
            }
        }
    }

    fun loadEntries(email: String) {
        viewModelScope.launch {
            dr.getEntriesByEmail(email).collect { entriesList ->
                _entries.value = entriesList
            }
        }
    }
}