package aut.bme.bookmanager

import android.app.Application
import aut.bme.bookmanager.ui.UIModule

class BookManagerApplication : Application() {
    lateinit var injector: BookManagerApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injector = DaggerBookManagerApplicationComponent.builder().uIModule(UIModule(this)).build()
    }
}