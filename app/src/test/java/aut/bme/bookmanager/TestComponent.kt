package aut.bme.bookmanager

import aut.bme.bookmanager.mock.MockNetworkModule
import aut.bme.bookmanager.test.BooksTest
import aut.bme.bookmanager.test.FavoritesTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockNetworkModule::class, TestModule::class])
interface TestComponent : BookManagerApplicationComponent {
    fun inject(booksTest: BooksTest)
    fun inject(favoritesTest: FavoritesTest)
}