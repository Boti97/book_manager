package aut.bme.bookmanager.interactor.repository

import android.content.Context
import aut.bme.bookmanager.model.Book

interface RepositoryInteractor {
    fun getBooks(context: Context)
    fun deleteBook(context: Context, book: Book)
    fun insertBooks(context: Context, books: List<Book>)
    fun updateBook(context: Context, book: Book)
    fun deleteAll(context: Context)
}