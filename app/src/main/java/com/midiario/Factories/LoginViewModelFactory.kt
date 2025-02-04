package com.midiario.Factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.midiario.ViewModels.LoginViewModel
import com.midiario.ViewModels.UsuarioViewModel

class LoginViewModelFactory(
    private val usuarioViewModel: UsuarioViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(usuarioViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
