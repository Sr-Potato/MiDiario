package com.midiario.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val usuarioViewModel: UsuarioViewModel) : ViewModel() {


    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        viewModelScope.launch {
            try {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(_email.value, _password.value).await()
                usuarioViewModel.setEmail(_email.value)
                Log.e("Login","Usuario logueado con email: ${_email.value}")

            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.e("Login", "Credenciales incorrectas: ${e.message}")
            } catch (e: FirebaseAuthInvalidUserException) {
                Log.e("Login", "Usuario no encontrado: ${e.message}")
            }catch (e: Exception) {
                Log.e("Login", "Error: ${e.message}")
            }
        }
    }
}
