package aut.bme.bookmanager.ui.favorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import aut.bme.bookmanager.R
import aut.bme.bookmanager.model.Book
import kotlinx.android.synthetic.main.card_book.view.*

class FavoriteAdapter constructor(
    private val context: Context,
    private var books: List<Book>
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = view.book_title
        var tvAuthor: TextView = view.book_author
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.card_book, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = books.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]

        holder.tvTitle.text = book.title
        holder.tvAuthor.text = book.author
    }
}