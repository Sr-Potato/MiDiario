@file:OptIn(ExperimentalMaterial3Api::class)

package com.midiario.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.midiario.R
import com.midiario.ViewModels.HomeViewModel
import com.midiario.data.dao.DiaryDatabase
import com.midiario.ViewModels.UsuarioViewModel
import com.midiario.Factories.HomeViewModelFactory
import com.midiario.data.model.EntryModel
import com.midiario.data.repository.DiaryRepository
import com.midiario.navigation.Nav

@Composable
fun Home(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val context = LocalContext.current
    val database = DiaryDatabase.getDatabase(context.applicationContext)
    val dao = database.diaryEntryDao()
    val dr = DiaryRepository(dao)

    val homeViewModel: HomeViewModel =
        viewModel(factory = HomeViewModelFactory(dr, usuarioViewModel))

    Spacer(modifier = Modifier.padding(start = 10.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HeaderHome(navController, homeViewModel)
        NuevoBodyHome(homeViewModel = homeViewModel, navController)
    }
}


@Composable
fun HeaderHome(navController: NavController, homeViewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Entradita(navController = navController, homeViewModel)
        //Searcher()
    }
}

@Composable
fun Entradita(navController: NavController, homeViewModel: HomeViewModel) {
    Row(horizontalArrangement = Arrangement.Center) {
        Text(
            text = "Entradas a tu diario...",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
        Spacer(modifier = Modifier.padding(start = 95.dp))
        FloatingAddButton(navController, homeViewModel)
    }
}


@Composable
fun note(entryModel: EntryModel, onClick: (EntryModel) -> Unit) {
    Card(
        onClick = { onClick(entryModel) },
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp),
        shape = ShapeDefaults.Large,
        colors = CardDefaults.cardColors(
            containerColor = Color.White, contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = entryModel.title)
            Text(text = entryModel.content.take(50))
        }
    }
}

@Composable
fun NuevoBodyHome(homeViewModel: HomeViewModel, navController: NavController) {

    val entries by homeViewModel.entries.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Define 2 columnas
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(entries) { entry ->
            note(entry) { selectedEntry ->
                navController.navigate("${Nav.Entry.route}/${selectedEntry.id}")
            }
        }
    }
}

@Composable
fun FloatingAddButton(navController: NavController, homeViewModel: HomeViewModel) {
    Button(
        onClick = {

            navController.navigate("${Nav.Entry.route}/-1")
        },
        colors = ButtonDefaults.buttonColors(
            Color.White, Color.Black
        ), modifier = Modifier.size(65.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icononew),
            contentDescription = "Agregar entrada",
            tint = Color.Black
        )
    }
}