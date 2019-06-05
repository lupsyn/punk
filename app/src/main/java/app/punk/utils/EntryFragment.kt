package app.punk.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import app.punk.R
import app.punk.api.Status
import app.punk.data.ExternalEntry
import app.punk.data.resultentities.EntryWithBeers
import app.punk.extensions.observeNotNull
import app.punk.home.HomeViewModel
import app.punk.home.HomeViewModelFactory
import app.punk.ui.ProgressTimeLatch
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_rv.*

@SuppressLint("ValidFragment")
abstract class EntryFragment<LI : EntryWithBeers<out ExternalEntry>, VM : EntryViewModel<LI>>(
    private val vmClass: Class<VM>
) : Fragment() {
    protected lateinit var viewModel: VM

    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var swipeRefreshLatch: ProgressTimeLatch
    private var originalRvTopPadding = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (vmClass == HomeViewModel::class.java) {
            viewModel = (HomeViewModelFactory(BeerInteractorModule.beerInteractor).create(vmClass) as VM)
        } else {
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(vmClass)
        }

//        controller = createController()
//        controller.callbacks = object : EntryGridEpoxyController.Callbacks<LI> {
//            override fun onItemClicked(item: LI) {
//                this@EntryGridFragment.onItemClicked(item)
//            }
//        }
//
//        GridToGridTransitioner.setupSecondFragment(this) {
//            grid_recyclerview?.itemAnimator = DefaultItemAnimator()
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        swipeRefreshLatch = ProgressTimeLatch(minShowTime = 1350) {
            grid_swipe_refresh?.isRefreshing = it
        }

        recyclerview.apply {
            // We set the item animator to null since it can interfere with the enter/shared element
            // transitions
            itemAnimator = null

//            setController(controller)
//            addItemDecoration(SpacingItemDecorator(paddingLeft))
        }
        //originalRvTopPadding = recyclerView.paddingTop

        grid_swipe_refresh.setOnRefreshListener(viewModel::refresh)

        viewModel.viewState.observeNotNull(this) {
            //controller.tmdbImageUrlProvider = it.tmdbImageUrlProvider
            //controller.submitList(it.liveList)

            it.liveList
            when (it.uiResource.status) {
                Status.SUCCESS -> {
                    swipeRefreshLatch.refreshing = false
                    Log.e("Tag", "is logging")
                    // controller.isLoading = false
                }
                Status.ERROR -> {
                    swipeRefreshLatch.refreshing = false
                    //controller.isLoading = false
                    Log.e("Tag", "Stop logging")
                    Snackbar.make(view, it.uiResource.message ?: "EMPTY", Snackbar.LENGTH_SHORT).show()
                }
                Status.REFRESHING -> swipeRefreshLatch.refreshing = true
                Status.LOADING_MORE -> {
                    Log.e("Tag", "Log more")
                    //   controller.isLoading = true
                }
            }

//            if (it.isLoaded) {
//                // First time we've had state, start any postponed transitions
//                scheduleStartPostponedTransitions()
//            }
        }
    }
//
//    abstract fun onItemClicked(item: LI)
//
//    open fun createController(): EntryGridEpoxyController<LI> {
//        return EntryGridEpoxyController()
//    }
}
