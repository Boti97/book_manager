package aut.bme.bookmanager.interactor.network

import aut.bme.bookmanager.model.NetworkConstants.API_KEY
import javax.inject.Inject

/*
 * Uses BooksApi methods to conduct the remote api calls.
 */
class BooksNetworkInteractor @Inject constructor(private var booksApi: BooksApi) {

    fun getBooks(author: String) {
        try {
            val booksAPICallResponse = booksApi.getBooks(author, API_KEY)
            val response = booksAPICallResponse.execute()

            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }
        } catch (e: Exception) {
        }
    }
}