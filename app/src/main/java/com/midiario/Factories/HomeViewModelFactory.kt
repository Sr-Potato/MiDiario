package com.midiario.Factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.midiario.ViewModels.HomeViewModel
import com.midiario.ViewModels.UsuarioViewModel
import com.midiario.data.repository.DiaryRepository

class HomeViewModelFactory(private val dr: DiaryRepository, private val usuarioViewModel: UsuarioViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dr, usuarioViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
