package com.midiario.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun signUp(onError: (String) -> Unit) {
        if (password.value.length < 6) {
            onError("La contraseña debe tener mas de 6 caracteres.")
        } else if (password.value != confirmPassword.value) {
            onError("Las contraseñas no coinciden")
        } else {
            viewModelScope.launch {
                try {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(_email.value, _password.value).await()
                } catch (e: Exception) {
                    Log.e("Register", "Error: ${e.message}")
                }
            }
        }
    }
}