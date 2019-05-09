package app.punk.utils

import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope

interface IPunkViewModel {
    val disposables: CompositeDisposable
    val scope: CoroutineScope
}