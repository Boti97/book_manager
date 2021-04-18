package aut.bme.bookmanager.interactor.event

data class TitleChangeEvent(
    var title: String = "",
    var position: Int = 0
)