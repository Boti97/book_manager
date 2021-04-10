package aut.bme.bookmanager.ui.books

import aut.bme.bookmanager.interactor.network.BooksNetworkInteractor
import javax.inject.Inject

class BooksPresenter @Inject constructor(private val booksNetworkInteractor: BooksNetworkInteractor) {
}