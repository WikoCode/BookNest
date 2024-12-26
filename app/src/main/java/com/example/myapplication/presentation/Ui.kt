package com.example.myapplication.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.Book
import com.example.myapplication.domain.AuthViewModel
import com.example.myapplication.domain.BookViewModel

@Composable
fun MainScreen(viewModel: BookViewModel) {
    val books by viewModel.books.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for a book") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.searchBooks(searchQuery) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Search")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        } else {
            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                items(books) { book: Book ->
                    BookItem(book)
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = book.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Author: ${book.author}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun LoginScreen(viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            viewModel.login(email, password) { success, error ->
                if (success) {
                    isSuccess = true
                } else {
                    errorMessage = error ?: "Unknown error"
                }
            }
        }) {
            Text("Login")
        }

        if (isSuccess) {
            Text("Login successful!")
        } else if (errorMessage.isNotEmpty()) {
            Text("Error: $errorMessage")
        }
    }
}
