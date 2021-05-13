package aut.bme.bookmanager.ui.books

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import aut.bme.bookmanager.R
import aut.bme.bookmanager.injector
import aut.bme.bookmanager.model.Book
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_books.*
import javax.inject.Inject

class BooksFragment : Fragment() {

    private val books: MutableList<Book> = mutableListOf()
    private var booksAdapter: BooksAdapter? = null

    @Inject
    lateinit var booksPresenter: BooksPresenter

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this)
        if (!booksPresenter.isAttached)
            booksPresenter.attachScreen(this)
    }

    override fun onDestroyView() {
        booksPresenter.detachScreen()
        super.onDestroyView()
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

        firebaseAnalytics = Firebase.analytics

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        books_rv.layoutManager = llm

        booksAdapter = BooksAdapter(requireContext(), books)
        books_rv.adapter = booksAdapter

        search_book_sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(title: String?): Boolean {
                if (title != null) {
                    booksPresenter.getBooks(title)
                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH) {
                        param(FirebaseAnalytics.Param.SEARCH_TERM, title)
                    }
                }
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

        ad_fav_btn.setOnClickListener {
            booksPresenter.addFavorites(requireContext(), books.filter { book -> book.isSelected })
            books.forEach { book -> book.isSelected = false }
            booksAdapter?.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Favorites added", Toast.LENGTH_SHORT).show()
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "button")
                param(FirebaseAnalytics.Param.ITEM_ID, "ad_fav_btn")
            }
        }
    }

    fun refreshBooks(newBooks: List<Book>?) {
        books.clear()
        if (newBooks != null)
            books.addAll(newBooks)
        booksAdapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "fragment")
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Books")
        }
    }
}