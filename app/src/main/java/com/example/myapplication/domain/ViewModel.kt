package com.example.myapplication.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AuthRepository
import com.example.myapplication.data.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject // Import for @Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {
    val books = repository.books
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.searchBooks(query)
            _isLoading.value = false
        }
    }
}


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        authRepository.login(email, password, onResult)
    }

    fun register(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        authRepository.register(email, password, onResult)
    }
}
