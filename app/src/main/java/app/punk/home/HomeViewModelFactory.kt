package app.punk.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.punk.interactors.UpdateBeersInteractor

class HomeViewModelFactory(
    private val interactor: UpdateBeersInteractor
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(interactor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}