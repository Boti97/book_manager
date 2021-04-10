package aut.bme.bookmanager.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import aut.bme.bookmanager.R
import aut.bme.bookmanager.model.Book
import kotlinx.android.synthetic.main.fragment_books.*
import javax.inject.Inject

class BooksFragment : Fragment() {

    private val books: MutableList<Book> = mutableListOf(
        Book(
            "Foundation",
            "Isaac Asimov"
        ),
        Book(
            "Second Foundation",
            "Isaac Asimov"
        )
    )
    private var booksAdapter: BooksAdapter? = null

    @Inject
    lateinit var booksPresenter: BooksPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        books_rv.layoutManager = llm

        booksAdapter = BooksAdapter(requireContext(), books)
        books_rv.adapter = booksAdapter
    }
}