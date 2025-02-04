package com.midiario.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.midiario.R
import com.midiario.ViewModels.RegisterViewModel
import com.midiario.navigation.Nav

@Composable
fun Register(navController: NavController) {
    ImagenFondo()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        HeaderRegister()
        BodyRegister(navController)
    }

}


@Composable
fun BodyRegister(navController: NavController, viewModel: RegisterViewModel = viewModel()) {
    val context = LocalContext.current
    val email by viewModel.email.collectAsState()
    val pass by viewModel.password.collectAsState()
    val confirmPass by viewModel.confirmPassword.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            colors = CardColors(Color.Black, Color.White, Color.Green, Color.Yellow)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //NameSurname()
                Email(email, onEmailChange = { viewModel.onEmailChange(it) })
                Pass(pass, onPassChange = { viewModel.onPasswordChange(it) })
                Pass(pass = confirmPass, onPassChange = { viewModel.onConfirmPasswordChange(it) })
                HaveAccount(navController)
                ButtonRegister(onSingUpClick = {
                    viewModel.signUp { errorMessage ->
                        Toast.makeText(context, errorMessage,Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}


@Composable
fun ButtonRegister(onSingUpClick: () -> Unit) {
    Button(
        onClick = onSingUpClick,
        modifier = Modifier.padding(top = 10.dp),
        colors = ButtonDefaults.buttonColors(
            Color.White, Color.Black
        )
    ) {
        Text(text = "Registro")
    }
}

@Composable
fun HeaderRegister() {
    Box(
        Modifier
            .clip(CircleShape)
            .background(Color.Black)
            .size(200.dp)
    ) {

        Icon(
            painterResource(id = R.drawable.welcome),
            "",
            tint = Color.White,
            modifier = Modifier
                .size(150.dp)
                //.align(Alignment.CenterHorizontally)
                .align(Alignment.Center)

        )
    }
}

@Composable
fun HaveAccount(navController: NavController) {
    Column(modifier = Modifier.padding(5.dp)) {
        Text(text = "¿Ya tienes cuenta?", modifier = Modifier
            .padding(top = 5.dp)
            .clickable { navController.navigate(Nav.Login.route) })
    }
}


@Preview
@Composable
fun PreviewRegister() {
    val navigationController = rememberNavController()
    Register(navController = navigationController)
}