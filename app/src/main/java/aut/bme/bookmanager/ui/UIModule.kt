package aut.bme.bookmanager.ui

import aut.bme.bookmanager.interactor.network.BooksNetworkInteractor
import aut.bme.bookmanager.interactor.repository.FavoriteBooksRepositoryInteractor
import aut.bme.bookmanager.ui.books.BooksPresenter
import aut.bme.bookmanager.ui.favorite.FavoritePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UIModule() {

    @Provides
    @Singleton
    fun provideBooksPresenter(
        booksNetworkInteractor: BooksNetworkInteractor,
        favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor
    ) =
        BooksPresenter(booksNetworkInteractor, favoriteBooksRepositoryInteractor)

    @Provides
    @Singleton
    fun provideFavoritePresenter(favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor) =
        FavoritePresenter(favoriteBooksRepositoryInteractor)
}