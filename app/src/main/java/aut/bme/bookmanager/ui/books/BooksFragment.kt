package aut.bme.bookmanager.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import aut.bme.bookmanager.R

class BooksFragment : Fragment() {

    private lateinit var booksViewModel: BooksViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        booksViewModel =
                ViewModelProvider(this).get(BooksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_books, container, false)
        val textView: TextView = root.findViewById(R.id.text_books)
        booksViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}