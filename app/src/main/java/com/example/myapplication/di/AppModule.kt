package com.example.myapplication.di

import javax.inject.Singleton
import androidx.room.Room
import com.example.myapplication.data.BookDatabase
import com.example.myapplication.data.BookDao
import com.example.myapplication.data.OpenLibraryApiService
import com.example.myapplication.data.BookRepository
import android.content.Context
import com.example.myapplication.data.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BookDatabase =
        Room.databaseBuilder(context, BookDatabase::class.java, "books.db").build()

    @Singleton
    @Provides
    fun provideBookDao(db: BookDatabase): BookDao = db.bookDao()

    @Singleton
    @Provides
    fun provideRetrofitApi(): OpenLibraryApiService = RetrofitInstance.api

    @Singleton
    @Provides
    fun provideRepository(api: OpenLibraryApiService, dao: BookDao): BookRepository =
        BookRepository(api, dao)
}
