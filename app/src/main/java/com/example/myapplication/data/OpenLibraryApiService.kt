package com.example.myapplication.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryApiService {
    @GET("search.json")
    suspend fun searchBooks(@Query("q") query: String): BookSearchResponse
}

data class BookSearchResponse(
    val docs: List<BookDto>
)

data class BookDto(
    val key: String,
    val title: String,
    val author_name: List<String>?
)

object RetrofitInstance {
    private const val BASE_URL = "https://openlibrary.org/"

    val api: OpenLibraryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenLibraryApiService::class.java)
    }
}
