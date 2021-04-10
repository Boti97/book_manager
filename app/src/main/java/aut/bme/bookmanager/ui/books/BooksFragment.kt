package aut.bme.bookmanager.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import aut.bme.bookmanager.R
import javax.inject.Inject

class BooksFragment : Fragment() {

    @Inject
    lateinit var booksPresenter: BooksPresenter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }
}