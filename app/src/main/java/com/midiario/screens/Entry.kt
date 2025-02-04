package com.midiario.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midiario.data.dao.DiaryDatabase
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.midiario.ViewModels.EntryViewModel
import com.midiario.ViewModels.HomeViewModel
import com.midiario.ViewModels.UsuarioViewModel
import com.midiario.Factories.EntryViewModelFactory
import com.midiario.data.repository.DiaryRepository
import com.midiario.navigation.Nav

@Composable
fun Entry(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    homeViewModel: HomeViewModel,
    viewModel: EntryViewModel = viewModel(
        factory = EntryViewModelFactory(
            DiaryRepository(DiaryDatabase.getDatabase(LocalContext.current).diaryEntryDao())
        )
    ),
    entryId: Int
) {

    LaunchedEffect(entryId) {
        if (entryId != -1) {
            viewModel.getEntryById(entryId)
        }
    }
    val entry by viewModel.entry.collectAsState()


    val emailState = usuarioViewModel.usuarioEmail.collectAsState(initial = "")

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(entry) {
        if (entryId != -1 && entry != null) {
            title = entry!!.title
            content = entry!!.content
        } else {
            title = ""
            content = ""
        }
    }

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            SaveButton {
                val email: String? = emailState.value
                if (title.isNotBlank() && content.isNotBlank() && !email.isNullOrEmpty()) {
                    if (entryId == -1) {
                        viewModel.insertEntry(title, content, email)
                    } else {
                        entry?.let { oldEntry ->
                            viewModel.updateEntry(
                                oldEntry.copy(title = title, content = content)
                            )
                        }
                    }
                    navController.navigate(Nav.Home.route)
                }
            }

            BackToHomeButton(navController)
            DeleteButton {
                val email = emailState.value
                val entryLocal = entry
                if (!email.isNullOrEmpty() && entryLocal != null) {
                    viewModel.deleteEntry(entryLocal) {
                        navController.navigate(Nav.Home.route)
                    }
                } else {
                    Log.e("Error al eliminar:", "Faltan datos para identificar la entrada.")
                }
            }
        }

        HeaderEntry(title) { title = it }
        BodyEntry(content) { content = it }
    }
}


@Composable
fun SaveButton(onSaveClick: () -> Unit) {
    Button(
        onClick = onSaveClick,
        modifier = Modifier
            .padding(end = 8.dp),
        colors = ButtonDefaults.buttonColors(
            Color.White, Color.Black
        )
    ) {
        Text(text = "Guardar")
    }
}

@Composable
fun DeleteButton(onDeleteClick: () -> Unit) {
    Button(
        onClick = onDeleteClick,
        modifier = Modifier
            .padding(start = 8.dp),
        colors = ButtonDefaults.buttonColors(
            Color.White, Color.Black
        )
    ) {
        Text(text = "Borrar")
    }
}

@Composable
fun HeaderEntry(title: String, onTitleChange: (String) -> Unit) {
    OutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        label = { Text("Escribe tu título aquí...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        maxLines = 1
    )
}

@Composable
fun BodyEntry(content: String, onContentChange: (String) -> Unit) {
    OutlinedTextField(
        value = content,
        onValueChange = onContentChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        label = { Text("Escribe tu entrada aquí...") },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        maxLines = Int.MAX_VALUE
    )
}

@Composable
fun BackToHomeButton(navController: NavController) {
    IconButton(
        onClick = {
            navController.navigate(Nav.Home.route)
        },
        modifier = Modifier
            .size(50.dp)
            .background(Color.White, shape = CircleShape)
            .padding(8.dp)

    ) {
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = "Home",
            tint = Color.Black
        )
    }
}