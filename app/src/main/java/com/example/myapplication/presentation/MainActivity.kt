package com.example.myapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.domain.AuthViewModel
import com.example.myapplication.domain.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookNestApp()
        }
    }
}


@Composable
fun BookNestApp() {
    val authViewModel: AuthViewModel = hiltViewModel()
    val bookViewModel: BookViewModel = hiltViewModel()

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(authViewModel) { success ->
                if (success) {
                    navController.navigate("main")
                }
            }
        }
        composable("register") {
            RegisterScreen(authViewModel) { success ->
                if (success) {
                    navController.navigate("login")
                }
            }
        }
        composable("main") {
            MainScreen(bookViewModel)
        }
    }
}
