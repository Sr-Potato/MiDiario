package com.midiario.navigation

sealed class Nav (val route: String){
    object Login: Nav("login")
    object Register: Nav("register")
    object Home : Nav("home")
    object Entry :Nav("entry")
}