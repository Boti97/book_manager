package aut.bme.bookmanager.interactor.network

import aut.bme.bookmanager.interactor.ApiClient
import aut.bme.bookmanager.interactor.event.BookResultEvent
import aut.bme.bookmanager.interactor.network.NetworkConstants.API_KEY
import aut.bme.bookmanager.model.BookResult
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/*
 * Uses BooksApi methods to conduct the remote api calls.
 */
class BooksNetworkInteractor @Inject constructor() {

    fun getBooks(title: String) {
        val bookResultEvent = BookResultEvent()

        try {
            val apiClient = ApiClient("api-key", API_KEY)
            val api = apiClient.createService(BooksApi::class.java);
            val booksAPICallResponse = api.getBooks(title)

            booksAPICallResponse.enqueue(object : Callback<BookResult> {
                override fun onResponse(call: Call<BookResult>, response: Response<BookResult>) {
                    if (response.code() == 200) {
                        bookResultEvent.code = response.code()
                        bookResultEvent.books = response.body()?.bookList
                        EventBus.getDefault().post(bookResultEvent)
                    }
                }

                override fun onFailure(call: Call<BookResult>, t: Throwable) {
                    bookResultEvent.throwable = t
                    EventBus.getDefault().post(bookResultEvent)
                }
            })
        } catch (e: Exception) {
            bookResultEvent.throwable = e
            EventBus.getDefault().post(bookResultEvent)
        }
    }
}