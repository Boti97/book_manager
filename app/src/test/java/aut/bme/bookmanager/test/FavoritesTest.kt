package aut.bme.bookmanager.test

import android.content.Context
import aut.bme.bookmanager.interactor.repository.FavoriteBooksRepositoryInteractor
import aut.bme.bookmanager.model.Book
import aut.bme.bookmanager.testInjector
import aut.bme.bookmanager.ui.favorite.FavoriteFragment
import aut.bme.bookmanager.ui.favorite.FavoritePresenter
import aut.bme.bookmanager.utils.argumentCaptor
import aut.bme.bookmanager.utils.mock
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
class FavoritesTest {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor

    @Inject
    lateinit var favoritePresenter: FavoritePresenter

    private lateinit var favoriteFragment: FavoriteFragment
    private var databaseManager = DatabaseManager()

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testInjector.inject(this)
        favoriteFragment = mock()
        favoritePresenter.attachScreen(favoriteFragment)
        databaseManager.clearDB(context)
    }

    @Test
    fun testGetBooks() {
        val title = "Foundation"
        val book = Book()
        book.title = title
        databaseManager.insertTestBook(context, book)

        favoritePresenter.getBooks(context)

        val bookList = argumentCaptor<MutableList<Book>>()
        verify(favoriteFragment).refreshFavorites(bookList.capture())

        assert(bookList.value.size == 1)
        assert(bookList.value[0].title.equals(title))
    }

    @Test
    fun testDeleteBook() {
        val title = "Foundation"
        val book = Book()
        book.bookId = 1
        book.title = title
        book.author = "Isaac Asimov"

        databaseManager.insertTestBook(context, book)

        favoritePresenter.deleteBook(context, book)

        val booksInDB = databaseManager.getBooksInDB(context)
        assert(booksInDB.isEmpty())

    }

    @Test
    fun testDeleteAll() {
        val title = "Foundation"
        val book = Book()
        book.title = title
        book.author = "Isaac Asimov"

        databaseManager.insertTestBook(context, book)

        favoritePresenter.deleteAll(context)

        val booksInDB = databaseManager.getBooksInDB(context)
        assert(booksInDB.isEmpty())
    }

    @Test
    fun testUpdateBook() {
        val title = "Foundation"
        val book = Book()

        book.title = title
        book.author = "Isaac Asimov"

        databaseManager.insertTestBook(context, book)

        val newTitle = "Foundation"
        book.title = newTitle

        favoritePresenter.updateBook(context, book)

        val booksInDB = databaseManager.getBooksInDB(context)
        assert(booksInDB.size == 1)
        assert(booksInDB[0].title.equals(newTitle))
    }

    @After
    fun tearDown() {
        favoritePresenter.detachScreen()
    }

}