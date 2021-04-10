package aut.bme.bookmanager.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import aut.bme.bookmanager.R
import aut.bme.bookmanager.model.Book
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private val favoriteBooks: MutableList<Book> = mutableListOf(
        Book(
            "Foundation",
            "Isaac Asimov"
        ),
        Book(
            "Second Foundation",
            "Isaac Asimov"
        )
    )
    private var favoriteAdapter: FavoriteAdapter? = null

    @Inject
    lateinit var favoritePresenter: FavoritePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        favorite_books_rv.layoutManager = llm

        favoriteAdapter = FavoriteAdapter(requireContext(), favoriteBooks)
        favorite_books_rv.adapter = favoriteAdapter
    }
}