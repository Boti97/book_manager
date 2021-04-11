package aut.bme.bookmanager.ui.books

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import aut.bme.bookmanager.R
import aut.bme.bookmanager.injector
import aut.bme.bookmanager.interactor.network.event.BookResultEvent
import aut.bme.bookmanager.model.Book
import kotlinx.android.synthetic.main.fragment_books.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class BooksFragment : Fragment() {

    private val books: MutableList<Book> = mutableListOf()
    private var booksAdapter: BooksAdapter? = null

    @Inject
    lateinit var booksPresenter: BooksPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

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

        search_book_sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(title: String?): Boolean {
                if (title != null) booksPresenter.getBooks(title)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        search_book_sv.setOnCloseListener {
            books.clear()
            booksAdapter?.notifyDataSetChanged()
            false
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBookResultEvent(event: BookResultEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
        } else {
            books.clear()
            books.addAll(event.books!!)
            booksAdapter?.notifyDataSetChanged()
        }
    }
}