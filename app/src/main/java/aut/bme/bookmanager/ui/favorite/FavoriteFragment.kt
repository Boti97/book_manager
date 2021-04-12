package aut.bme.bookmanager.ui.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import aut.bme.bookmanager.R
import aut.bme.bookmanager.injector
import aut.bme.bookmanager.interactor.event.BookResultEvent
import aut.bme.bookmanager.model.Book
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private val favoriteBooks: MutableList<Book> = mutableListOf()
    private var favoriteAdapter: FavoriteAdapter? = null

    @Inject
    lateinit var favoritePresenter: FavoritePresenter

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
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritePresenter.getBooks(requireContext())

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        favorite_books_rv.layoutManager = llm

        favoriteAdapter = FavoriteAdapter(requireContext(), favoriteBooks)
        favorite_books_rv.adapter = favoriteAdapter
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBookResultEvent(event: BookResultEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
        } else {
            favoriteBooks.clear()
            favoriteBooks.addAll(event.books!!)
            favoriteAdapter?.notifyDataSetChanged()
        }
    }
}