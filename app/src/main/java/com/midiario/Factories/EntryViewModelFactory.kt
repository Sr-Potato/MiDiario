package com.midiario.Factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.midiario.ViewModels.EntryViewModel
import com.midiario.data.repository.DiaryRepository

class EntryViewModelFactory(private val dr: DiaryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntryViewModel(dr) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
