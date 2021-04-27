package aut.bme.bookmanager.test

import android.content.Context
import aut.bme.bookmanager.interactor.repository.BookDatabase
import aut.bme.bookmanager.interactor.repository.FavoriteBooksRepositoryInteractor
import aut.bme.bookmanager.model.Book
import aut.bme.bookmanager.testInjector
import aut.bme.bookmanager.ui.books.BooksFragment
import aut.bme.bookmanager.ui.books.BooksPresenter
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
class BooksTest {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var favoriteBooksRepositoryInteractor: FavoriteBooksRepositoryInteractor

    @Inject
    lateinit var booksPresenter: BooksPresenter

    private lateinit var booksFragment: BooksFragment
    private var databaseManager = DatabaseManager()

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testInjector.inject(this)
        booksFragment = mock()
        booksPresenter.attachScreen(booksFragment)
        databaseManager.getDB(context)
        databaseManager.clearDB()
    }

    @Test
    fun testBooks() {
        val title = "Foundation"
        booksPresenter.getBooks(title)

        val bookList = argumentCaptor<MutableList<Book>>()
        verify(booksFragment).refreshBooks(bookList.capture())
        assert(bookList.value.isNotEmpty())
        assert(bookList.value.size == 1)
        assert(bookList.value[0].title.equals(title))
    }

    @Test
    fun testAddFavorites() {
        val book = Book()
        book.author = "Isaac Asimov"
        book.title = "Foundation's Edge"

        booksPresenter.addFavorites(context, arrayListOf(book))
        val booksInDB = databaseManager.getBooksInDB()
        assert(booksInDB.size == 1)
        assert(booksInDB[0].title == book.title)
        assert(booksInDB[0].author == book.author)
    }

    @After
    fun tearDown() {
        booksPresenter.detachScreen()
        BookDatabase.getInstance(context).close()
    }
}