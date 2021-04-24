package aut.bme.bookmanager.ui.favorite

import android.content.Context
import aut.bme.bookmanager.interactor.event.BookResultEvent
import aut.bme.bookmanager.interactor.event.TitleChangeEvent
import aut.bme.bookmanager.interactor.repository.FavoriteBooksRepositoryInteractor
import aut.bme.bookmanager.model.Book
import aut.bme.bookmanager.ui.Presenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class FavoritePresenter @Inject constructor(
    private val executor: Executor,
    private val favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor
) : Presenter<FavoriteFragment>() {

    var isAttached: Boolean = false

    fun getBooks(context: Context) {
        executor.execute {
            favoriteBooksRepositoryInteractor.getBooks(context)
        }
    }

    fun deleteBook(context: Context, book: Book) {
        executor.execute {
            favoriteBooksRepositoryInteractor.deleteBook(context, book)
        }
    }

    fun deleteAll(context: Context) {
        executor.execute {
            favoriteBooksRepositoryInteractor.deleteAll(context)
        }
    }

    fun updateBook(context: Context, book: Book) {
        executor.execute {
            favoriteBooksRepositoryInteractor.updateBook(context, book)
        }
    }

    override fun attachScreen(screen: FavoriteFragment) {
        super.attachScreen(screen)
        isAttached = true
        EventBus.getDefault().register(this)
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
            screen?.refreshFavorites(event.books!! as MutableList<Book>)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBookResultEvent(event: TitleChangeEvent) {
        screen?.updateBookTitle(event.position, event.title)
    }
}