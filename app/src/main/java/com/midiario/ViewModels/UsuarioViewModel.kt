package com.midiario.ViewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsuarioViewModel() : ViewModel(){

    private val _usuarioEmail = MutableStateFlow<String?>("")
    val usuarioEmail : StateFlow<String?> get() = _usuarioEmail

    fun setEmail(email:String){
        _usuarioEmail.value=email
    }
}