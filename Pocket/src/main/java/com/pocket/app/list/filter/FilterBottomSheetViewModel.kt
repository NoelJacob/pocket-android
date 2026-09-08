package com.pocket.app.list.filter

import com.pocket.app.list.SavesTab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ideashower.readitlater.R
import com.pocket.app.list.list.ListManager
import com.pocket.app.list.list.ListStatus
import com.pocket.sdk.api.generated.enums.ItemFilterKey
import com.pocket.sdk.api.generated.enums.ItemSortKey
import com.pocket.util.StringLoader
import com.pocket.util.collect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FilterBottomSheetViewModel @Inject constructor(
    private val listManager: ListManager,
    private val stringLoader: StringLoader
) : ViewModel(), SortFilterInteractions {

    private val _uiState = MutableStateFlow(SortFilterUiState())
    val uiState: StateFlow<SortFilterUiState> = _uiState

    private lateinit var savesTab: SavesTab

    override fun onInitialized(
        savesTab: SavesTab
    ) {
        this.savesTab = savesTab
        listManager.sortFilterState.collect(viewModelScope) { state ->
            val wordCountBasedAvailable = state.listStatus == ListStatus.SAVES
            val sort = when {
                wordCountBasedAvailable -> state.sort
                else -> when (state.sort) {
                    ItemSortKey.SHORTEST, ItemSortKey.LONGEST -> {
                        // Not available in v3 API, use a fallback.
                        ItemSortKey.NEWEST
                    }
                    else -> state.sort
                }

            }
            _uiState.value = _uiState.value.copy(
                filters = FiltersState(
                    viewed = SortFilterRowState(
                        checked = state.filters.contains(ItemFilterKey.VIEWED)
                    ),
                    notViewed = SortFilterRowState(
                        checked = state.filters.contains(ItemFilterKey.NOT_VIEWED)
                    ),
                    shortReads = SortFilterRowState(
                        visible = wordCountBasedAvailable,
                        checked = state.filters.contains(ItemFilterKey.SHORT_READS)
                    ),
                    longReads = SortFilterRowState(
                        visible = wordCountBasedAvailable,
                        checked = state.filters.contains(ItemFilterKey.LONG_READS)
                    )
                ),
                sortOrders = SortOrdersState(
                    newest = SortFilterRowState(
                        checked = sort == ItemSortKey.NEWEST
                    ),
                    oldest = SortFilterRowState(
                        checked = sort == ItemSortKey.OLDEST
                    ),
                    shortest = SortFilterRowState(
                        visible = wordCountBasedAvailable,
                        checked = sort == ItemSortKey.SHORTEST
                    ),
                    longest = SortFilterRowState(
                        visible = wordCountBasedAvailable,
                        checked = sort == ItemSortKey.LONGEST
                    )
                ),
                sortNewestLabel = if (state.listStatus == ListStatus.SAVES) {
                    stringLoader.getString(R.string.lb_sort_by_newest)
                } else {
                    stringLoader.getString(R.string.lb_sort_by_newest_archive)
                },
                sortOldestLabel = if (state.listStatus == ListStatus.SAVES) {
                    stringLoader.getString(R.string.lb_sort_by_oldest)
                } else {
                    stringLoader.getString(R.string.lb_sort_by_oldest_archive)
                }
            )
        }
    }

    override fun onNewestClicked() {
        listManager.updateCurrentSort(ItemSortKey.NEWEST)
    }

    override fun onOldestClicked() {
        listManager.updateCurrentSort(ItemSortKey.OLDEST)
    }

    override fun onShortestClicked() {
        listManager.updateCurrentSort(ItemSortKey.SHORTEST)
    }

    override fun onLongestClicked() {
        listManager.updateCurrentSort(ItemSortKey.LONGEST)
    }

    override fun onViewedClicked() {
        listManager.onFilterToggled(ItemFilterKey.VIEWED)
    }

    override fun onNotViewedClicked() {
        listManager.onFilterToggled(ItemFilterKey.NOT_VIEWED)
    }

    override fun onShortReadsClicked() {
        listManager.onFilterToggled(ItemFilterKey.SHORT_READS)
    }

    override fun onLongReadsClicked() {
        listManager.onFilterToggled(ItemFilterKey.LONG_READS)
    }
}

data class SortFilterUiState(
    val sortOrders: SortOrdersState = SortOrdersState(),
    val filters: FiltersState = FiltersState(),
    val sortNewestLabel: String = "",
    val sortOldestLabel: String = ""
)

data class SortOrdersState(
    val newest: SortFilterRowState = SortFilterRowState(),
    val oldest: SortFilterRowState = SortFilterRowState(),
    val shortest: SortFilterRowState = SortFilterRowState(),
    val longest: SortFilterRowState = SortFilterRowState()
)

data class FiltersState(
    val viewed: SortFilterRowState = SortFilterRowState(),
    val notViewed: SortFilterRowState = SortFilterRowState(),
    val shortReads: SortFilterRowState = SortFilterRowState(),
    val longReads: SortFilterRowState = SortFilterRowState()
)

data class SortFilterRowState(
    val visible: Boolean = true,
    val checked: Boolean = false
)

interface SortFilterInteractions {
    fun onInitialized(savesTab: SavesTab)
    fun onNewestClicked()
    fun onOldestClicked()
    fun onShortestClicked()
    fun onLongestClicked()
    fun onViewedClicked()
    fun onNotViewedClicked()
    fun onShortReadsClicked()
    fun onLongReadsClicked()
}