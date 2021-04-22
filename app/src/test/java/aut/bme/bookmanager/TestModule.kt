package aut.bme.bookmanager

import android.content.Context
import aut.bme.bookmanager.interactor.network.BooksNetworkInteractor
import aut.bme.bookmanager.interactor.repository.FavoriteBooksRepositoryInteractor
import aut.bme.bookmanager.ui.books.BooksPresenter
import aut.bme.bookmanager.ui.favorite.FavoritePresenter
import aut.bme.bookmanager.utils.UiExecutor
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module
class TestModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

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
    fun provideFavoritePresenter(favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor) =
        FavoritePresenter(favoriteBooksRepositoryInteractor)

    @Provides
    @Singleton
    fun provideNetworkExecutor(): Executor = UiExecutor()
}