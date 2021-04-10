package aut.bme.bookmanager.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import aut.bme.bookmanager.R
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var favoritePresenter: FavoritePresenter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
}