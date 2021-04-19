package aut.bme.bookmanager.interactor.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import aut.bme.bookmanager.model.Book

@Database(entities = [Book::class], version = 4)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDAO(): BookDAO

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null
        fun getInstance(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java, "book_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}