package app.punk.utils

import androidx.paging.PagedList
import app.punk.api.UiResource

data class EntryViewState<LI>(
    val uiResource: UiResource,
    val liveList: PagedList<LI>,
    val isLoaded: Boolean
)