package aut.bme.bookmanager

import aut.bme.bookmanager.mock.MockNetworkModule
import aut.bme.bookmanager.test.component.BooksComponentTest
import aut.bme.bookmanager.test.component.FavoritesComponentTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockNetworkModule::class, TestModule::class])
interface TestComponent : BookManagerApplicationComponent {
    fun inject(booksComponentTest: BooksComponentTest)
    fun inject(favoritesComponentTest: FavoritesComponentTest)
}