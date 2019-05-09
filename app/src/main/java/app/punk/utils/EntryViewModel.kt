package app.punk.utils


import androidx.lifecycle.LiveDataReactiveStreams
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import app.punk.api.Status
import app.punk.api.UiResource
import app.punk.data.ExternalEntry
import app.punk.data.resultentities.EntryWithBeers
import app.punk.extensions.toFlowable
import app.punk.util.AppCoroutineDispatchers
import app.punk.util.AppSchedulers
import app.punk.util.Logger
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.Flowables
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.launch

abstract class EntryViewModel<LI : EntryWithBeers<out ExternalEntry>>(
    schedulers: AppSchedulers,
    private val dispatchers: AppCoroutineDispatchers,
    dataSource: DataSource.Factory<Int, LI>?,
    private val logger: Logger,
    private val pageSize: Int = 21
) : PunkViewModel() {
    private val messages = BehaviorSubject.create<UiResource>()
    private val loaded = BehaviorSubject.createDefault(false)

    private val pageListConfig = PagedList.Config.Builder().run {
        setPageSize(pageSize * 3)
        setPrefetchDistance(pageSize)
        setEnablePlaceholders(false)
        build()
    }

    val viewState = LiveDataReactiveStreams.fromPublisher(
        Flowables.combineLatest(
            messages.toFlowable(),
            RxPagedListBuilder<Int, LI>(dataSource!!, pageListConfig)
                .setBoundaryCallback(object : PagedList.BoundaryCallback<LI>() {
                    override fun onItemAtEndLoaded(itemAtEnd: LI) = onListScrolledToEnd()

                    override fun onItemAtFrontLoaded(itemAtFront: LI) {
                        loaded.onNext(true)
                    }

                    override fun onZeroItemsLoaded() {
                        loaded.onNext(true)
                    }
                })
                .setFetchScheduler(schedulers.io)
                .setNotifyScheduler(schedulers.main)
                .buildFlowable(BackpressureStrategy.LATEST)
                .distinctUntilChanged(),
            loaded.toFlowable(),
            ::EntryViewState
        )
    )

    init {
        refresh()
    }

    fun onListScrolledToEnd() {
        scope.launch {
            sendMessage(UiResource(Status.LOADING_MORE))
            try {
                callLoadMore()
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun refresh() {
        scope.launch {
            sendMessage(UiResource(Status.REFRESHING))
            try {
                callRefresh()
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    protected open suspend fun callRefresh() = Unit

    protected open suspend fun callLoadMore() = Unit

    private fun onError(t: Throwable) {
        logger.e(t)
        sendMessage(UiResource(Status.ERROR, t.localizedMessage))
    }

    private fun onSuccess() {
        sendMessage(UiResource(Status.SUCCESS))
    }

    private fun sendMessage(uiResource: UiResource) {
        messages.onNext(uiResource)
    }
}