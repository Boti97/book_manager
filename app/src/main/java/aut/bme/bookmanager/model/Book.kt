package aut.bme.bookmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "book")
class Book {

    @PrimaryKey(autoGenerate = true)
    var bookId: Long? = null

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String? = null

    @ColumnInfo(name = "author")
    @SerializedName("author")
    var author: String? = null
}