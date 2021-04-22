package aut.bme.bookmanager.mock

import aut.bme.bookmanager.interactor.network.BooksApi
import aut.bme.bookmanager.model.Book
import aut.bme.bookmanager.model.BookResult
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MockBooksApi : BooksApi {
    override fun addBookToFavorites(body: Book?): Call<Void> {
        TODO("Not yet implemented")
    }

    override fun updateFavoriteBook(bookId: Long?, title: String?): Call<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteFavoriteBook(bookId: Long?): Call<Void> {
        TODO("Not yet implemented")
    }

    override fun getBooks(title: String, apiKey: String): Call<BookResult> {
        val bookResultEvent = BookResult()

        val books = ArrayList<Book>()
        val book = Book()
        book.author = "Isaac Asimov"
        book.title = "Foundation"

        books.add(book)
        bookResultEvent.books = books

        return object : Call<BookResult> {
            override fun execute(): Response<BookResult> {
                return Response.success(bookResultEvent)
            }

            override fun clone(): Call<BookResult> {
                return this
            }

            override fun enqueue(callback: Callback<BookResult>) {}

            override fun isExecuted(): Boolean {
                return false
            }

            override fun cancel() {}

            override fun isCanceled(): Boolean {
                return false
            }

            override fun request(): Request? {
                return null
            }
        }
    }
}