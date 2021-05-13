package aut.bme.bookmanager.ui.favorite

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aut.bme.bookmanager.R
import aut.bme.bookmanager.injector
import aut.bme.bookmanager.model.Book
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    private val favoriteBooks: MutableList<Book> = mutableListOf()
    private var favoriteAdapter: FavoriteAdapter? = null

    @Inject
    lateinit var favoritePresenter: FavoritePresenter

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this)
        if (!favoritePresenter.isAttached)
            favoritePresenter.attachScreen(this)
    }

    override fun onDestroyView() {
        favoritePresenter.detachScreen()
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAnalytics = Firebase.analytics

        favoritePresenter.getBooks(requireContext())

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        favorite_books_rv.layoutManager = llm

        favoriteAdapter = FavoriteAdapter(requireContext(), view, favoriteBooks)
        favorite_books_rv.adapter = favoriteAdapter

        view.foreground.alpha = 0

        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                favoritePresenter.deleteBook(requireContext(), favoriteBooks[pos])
                favoriteBooks.removeAt(pos)
                favoriteAdapter!!.notifyItemRemoved(pos)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(favorite_books_rv)

        clear_favorites_btn.setOnClickListener {
            favoritePresenter.deleteAll(requireContext())
            favoriteBooks.clear()
            favoriteAdapter?.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Favorites cleared", Toast.LENGTH_SHORT).show()

            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_ID, "clear_favorites_btn")
                param(FirebaseAnalytics.Param.ITEM_NAME, "AddFavorites")
                param(FirebaseAnalytics.Param.CONTENT_TYPE, "button")
            }
        }
    }

    fun refreshFavorites(favorites: List<Book>) {
        favoriteBooks.clear()
        favoriteBooks.addAll(favorites)
        favoriteAdapter?.notifyDataSetChanged()
    }

    fun updateBookTitle(position: Int, title: String) {
        favoriteBooks[position].title = title
        favoritePresenter.updateBook(requireContext(), favoriteBooks[position])
        favoriteAdapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "fragment")
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Favorites")
        }
    }
}