package aut.bme.bookmanager.ui.favorite

import android.content.Context
import aut.bme.bookmanager.interactor.repository.FavoriteBooksRepositoryInteractor
import javax.inject.Inject

class FavoritePresenter @Inject constructor(private val booksRepositoryInteractor: FavoriteBooksRepositoryInteractor) {

    fun getBooks(context: Context) {
        booksRepositoryInteractor.getBooks(context)
    }
}