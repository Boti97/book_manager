package aut.bme.bookmanager

import aut.bme.bookmanager.interactor.network.NetworkModule
import aut.bme.bookmanager.ui.MainActivity
import aut.bme.bookmanager.ui.UIModule
import aut.bme.bookmanager.ui.books.BooksFragment
import aut.bme.bookmanager.ui.favorite.FavoriteFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [UIModule::class, NetworkModule::class])
interface BookManagerApplicationComponent {
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(booksFragment: BooksFragment)
    fun inject(mainActivity: MainActivity)
}