package com.midiario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midiario.ViewModels.UsuarioViewModel
import com.midiario.Factories.UsuarioViewModelFactory
import com.midiario.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val usuarioViewModel: UsuarioViewModel = viewModel(factory = UsuarioViewModelFactory())
            AppNavigation(usuarioViewModel)
        }
    }
}
