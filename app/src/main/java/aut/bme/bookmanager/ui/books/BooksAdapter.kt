package aut.bme.bookmanager.ui.books

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import aut.bme.bookmanager.R
import aut.bme.bookmanager.model.Book
import kotlinx.android.synthetic.main.card_book.view.*

class BooksAdapter constructor(
    private val context: Context,
    private var books: List<Book>
) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = view.book_title
        var tvAuthor: TextView = view.book_author
        var cbSelected: CheckBox = view.select_cb
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
        holder.cbSelected.setOnCheckedChangeListener(null)
        holder.cbSelected.setChecked(book.isSelected)

        holder.cbSelected.setOnCheckedChangeListener { _, isChecked ->
            book.isSelected = isChecked
        }
    }
}