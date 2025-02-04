package com.midiario.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.midiario.ViewModels.UsuarioViewModel
import com.midiario.data.dao.DiaryDatabase
import com.midiario.data.repository.DiaryRepository
import com.midiario.screens.Entry
import com.midiario.screens.Home
import com.midiario.ViewModels.HomeViewModel
import com.midiario.Factories.HomeViewModelFactory
import com.midiario.screens.Login
import com.midiario.screens.Register

@Composable
fun AppNavigation(usuarioViewModel: UsuarioViewModel) {
    val navController = rememberNavController()

    val context = LocalContext.current
    val database = DiaryDatabase.getDatabase(context.applicationContext)
    val dao = database.diaryEntryDao()
    val dr = DiaryRepository(dao)
    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(dr, usuarioViewModel)
    )

    NavHost(navController = navController, startDestination = Nav.Login.route) {
        composable(route = Nav.Login.route) {
            Login(
                onLoginClick = {
                    val email = usuarioViewModel.usuarioEmail.value
                    if (!email.isNullOrEmpty()) {
                        usuarioViewModel.setEmail(email) // Guardar el email en el ViewModel
                        navController.navigate(Nav.Home.route) // Navegar a Home
                    }
                },
                navController = navController,
                usuarioViewModel
            )
        }

        composable(route = Nav.Register.route) {
            Register(navController)
        }
        composable(route = Nav.Home.route) {
            Home(navController, usuarioViewModel = usuarioViewModel)
        }

        composable(
            route = "${Nav.Entry.route}/{entryId}",
            arguments = listOf(navArgument("entryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getInt("entryId") ?: 0
            Entry(
                navController = navController,
                usuarioViewModel = usuarioViewModel,
                homeViewModel = homeViewModel,
                entryId = entryId
            )
        }
    }
}