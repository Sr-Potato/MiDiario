package com.midiario.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.midiario.R
import com.midiario.ViewModels.LoginViewModel
import com.midiario.ViewModels.UsuarioViewModel
import com.midiario.Factories.LoginViewModelFactory
import com.midiario.navigation.Nav

@Composable
fun Login(
    onLoginClick: () -> Unit,
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {

    val loginViewModelFactory = LoginViewModelFactory(usuarioViewModel)

    val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)

    ImagenFondo()
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Header()
        BodyLogin(
            navController = navController,
            onLoginClick = onLoginClick,
            viewModel = loginViewModel
        )
    }
}


@Composable
fun ImagenFondo() {
    Box {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UsuarioFoto()
    }
}

@Composable
fun UsuarioFoto() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .size(200.dp)
            .background(Color.Black)

    ) {
        Image(
            painter = painterResource(id = R.drawable.usuario),
            contentDescription = "Imagen de usuario",
            modifier = Modifier.size(200.dp)
        )
    }
}


@Composable
fun Email(email: String, onEmailChange: (String) -> Unit) {
    Column {
        Text(text = "Correo electrónico", modifier = Modifier.padding(5.dp), color = Color.White)
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            textStyle = TextStyle(Color.White),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Pass(pass: String, onPassChange: (String) -> Unit) {
    Column {
        Text(text = "Contraseña", modifier = Modifier.padding(5.dp))
        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            value = pass,
            onValueChange = onPassChange,
            textStyle = TextStyle(Color.White),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun OlvidoPass() {
    Column(modifier = Modifier.padding(5.dp)) {
        Text(text = "¿Olvidaste tu contraseña?", modifier = Modifier
            .padding(5.dp)
            .clickable { })
    }
}

@Composable
fun Botones(navController: NavController, onLoginClick: () -> Unit) {
    Row {
        Button(
            onClick = onLoginClick,
            modifier = Modifier.padding(5.dp),
            colors = ButtonDefaults.buttonColors(
                Color.White, Color.Black
            )
        ) {
            Text(text = "Iniciar sesion")
        }
        Button(
            onClick = { navController.navigate(Nav.Register.route) },
            modifier = Modifier.padding(5.dp),
            colors = ButtonDefaults.buttonColors(
                Color.White, Color.Black
            )
        ) {
            Text(text = "Registrarse")
        }
    }
}

@Composable
fun BodyLogin(
    onLoginClick: () -> Unit,
    navController: NavController,
    viewModel: LoginViewModel
) {
    val email by viewModel.email.collectAsState()
    val pass by viewModel.password.collectAsState()


    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            colors = CardColors(Color.Black, Color.White, Color.Green, Color.Yellow)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Email(email = email, onEmailChange = { viewModel.onEmailChange(it) })
                Pass(pass = pass, onPassChange = { viewModel.onPasswordChange(it) })
                OlvidoPass()
                Botones(navController = navController, onLoginClick = {
                    viewModel.login()
                    onLoginClick()
                })
            }
        }
    }
}