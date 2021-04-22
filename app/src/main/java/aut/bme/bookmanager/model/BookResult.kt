package aut.bme.bookmanager.model

import com.google.gson.annotations.SerializedName

/*
 * A wrapper class of the book result from the api
 */

class BookResult {
    @SerializedName("results")
    var books: List<Book> = ArrayList()
}