package aut.bme.bookmanager.ui.books

import android.content.Context
import aut.bme.bookmanager.interactor.event.BookResultEvent
import aut.bme.bookmanager.interactor.network.BooksNetworkInteractor
import aut.bme.bookmanager.interactor.repository.RepositoryInteractor
import aut.bme.bookmanager.model.Book
import aut.bme.bookmanager.ui.Presenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class BooksPresenter @Inject constructor(
    private val executor: Executor,
    private val booksNetworkInteractor: BooksNetworkInteractor,
    private val favoriteBooksRepositoryInteractor: RepositoryInteractor
) : Presenter<BooksFragment>() {

    var isAttached: Boolean = false

    fun getBooks(title: String) {
        executor.execute {
            booksNetworkInteractor.getBooks(title)
        }
    }

    fun addFavorites(context: Context, books: List<Book>) {
        favoriteBooksRepositoryInteractor.insertBooks(context, books)
    }

    override fun attachScreen(screen: BooksFragment) {
        super.attachScreen(screen)
        EventBus.getDefault().register(this)
        isAttached = true
    }

    override fun detachScreen() {
        EventBus.getDefault().unregister(this)
        isAttached = false
        super.detachScreen()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBookResultEvent(event: BookResultEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
        } else {
            screen?.refreshBooks(event.books!! as MutableList<Book>)
        }
    }
}