package aut.bme.bookmanager.ui.favorite

import android.content.Context
import aut.bme.bookmanager.interactor.repository.FavoriteBooksRepositoryInteractor
import aut.bme.bookmanager.model.Book
import javax.inject.Inject

class FavoritePresenter @Inject constructor(private val favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor) {

    fun getBooks(context: Context) {
        favoriteBooksRepositoryInteractor.getBooks(context)
    }

    fun deleteBook(context: Context, book: Book) {
        favoriteBooksRepositoryInteractor.deleteBook(context, book)
    }

    fun deleteAll(context: Context) {
        favoriteBooksRepositoryInteractor.deleteAll(context)
    }

    fun updateBook(context: Context, book: Book) {
        favoriteBooksRepositoryInteractor.updateBook(context, book)
    }
}