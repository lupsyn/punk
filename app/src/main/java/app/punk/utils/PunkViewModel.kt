package app.punk.utils

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Simple ViewModel which exposes a [CompositeDisposable] and [Job] which are automatically cleared/stopped when
 * the ViewModel is cleared.
 */
open class PunkViewModel : ViewModel(), IPunkViewModel {
    private val job = Job()

    override val disposables = CompositeDisposable()
    override val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        job.cancel()
    }
}