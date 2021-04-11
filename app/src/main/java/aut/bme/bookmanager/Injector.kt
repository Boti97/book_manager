package aut.bme.bookmanager

import android.app.Activity
import androidx.fragment.app.Fragment

val Activity.injector: BookManagerApplicationComponent
    get() {
        return (this.applicationContext as BookManagerApplication).injector
    }

val Fragment.injector: BookManagerApplicationComponent
    get() {
        return (this.requireContext().applicationContext as BookManagerApplication).injector
    }