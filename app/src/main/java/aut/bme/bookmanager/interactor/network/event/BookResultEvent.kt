package aut.bme.bookmanager.interactor.network.event

import aut.bme.bookmanager.model.Book

data class BookResultEvent(
    var code: Int = 0,
    var books: List<Book>? = null,
    var throwable: Throwable? = null
)
