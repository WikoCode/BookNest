package com.example.myapplication.data

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class BookRepository(
    private val api: OpenLibraryApiService,
    private val dao: BookDao
) {
    val books: LiveData<List<Book>> = dao.getAllBooks()

    suspend fun searchBooks(query: String) {
        val response = api.searchBooks(query)
        val books = response.docs.map {
            Book(id = it.key, title = it.title, author = it.author_name?.firstOrNull() ?: "Unknown")
        }
        dao.insertBooks(books)
    }
}


class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun register(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }
}
