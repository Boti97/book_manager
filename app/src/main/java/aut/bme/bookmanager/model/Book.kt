package aut.bme.bookmanager.model

import com.google.gson.annotations.SerializedName

class Book {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("author")
    var author: String? = null
}