package aut.bme.bookmanager.ui

import android.content.Context
import aut.bme.bookmanager.interactor.network.BooksNetworkInteractor
import aut.bme.bookmanager.interactor.repository.FavoriteBooksRepositoryInteractor
import aut.bme.bookmanager.ui.books.BooksPresenter
import aut.bme.bookmanager.ui.favorite.FavoritePresenter
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class UIModule(private val context: Context) {

    @Provides
    fun context() = context

    @Provides
    @Singleton
    fun provideBooksPresenter(
        executor: Executor,
        booksNetworkInteractor: BooksNetworkInteractor,
        favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor
    ) =
        BooksPresenter(executor, booksNetworkInteractor, favoriteBooksRepositoryInteractor)

    @Provides
    @Singleton
    fun provideFavoritePresenter(executor: Executor, favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor) =
        FavoritePresenter(executor, favoriteBooksRepositoryInteractor)

    @Provides
    @Singleton
    fun networkExecutor(): Executor = Executors.newFixedThreadPool(1)
}