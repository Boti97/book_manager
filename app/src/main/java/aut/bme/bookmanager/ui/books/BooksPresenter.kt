package aut.bme.bookmanager.ui.books

import android.content.Context
import aut.bme.bookmanager.interactor.network.BooksNetworkInteractor
import aut.bme.bookmanager.interactor.repository.FavoriteBooksRepositoryInteractor
import aut.bme.bookmanager.model.Book
import javax.inject.Inject

class BooksPresenter @Inject constructor(
    private val booksNetworkInteractor: BooksNetworkInteractor,
    private val favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor
) {

    fun getBooks(title: String) {
        booksNetworkInteractor.getBooks(title)
    }

    fun addFavorites(context: Context, books: List<Book>) {
        favoriteBooksRepositoryInteractor.insertBooks(context, books)
    }
}