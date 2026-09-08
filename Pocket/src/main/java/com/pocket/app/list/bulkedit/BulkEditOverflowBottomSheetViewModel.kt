package com.pocket.app.list.bulkedit

import com.pocket.app.list.SavesTab

import androidx.lifecycle.ViewModel
import com.ideashower.readitlater.R
import com.pocket.repository.ItemRepository
import com.pocket.sdk.api.generated.thing.Item
import com.pocket.util.StringLoader
import com.pocket.util.edit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BulkEditOverflowBottomSheetViewModel @Inject constructor(
    private val stringLoader: StringLoader,
    private val itemRepository: ItemRepository
): ViewModel(), BulkEditOverflowBottomSheetInteractions {

    private val _uiState = MutableStateFlow(BulkEditOverflowBottomSheetUiState())
    val uiState: StateFlow<BulkEditOverflowBottomSheetUiState> = _uiState

    private val _navigationEvents = MutableSharedFlow<BulkEditOverflowNavigationEvent>(extraBufferCapacity = 1)
    val navigationEvents: SharedFlow<BulkEditOverflowNavigationEvent> = _navigationEvents

    private var items: List<Item> = listOf()
    private lateinit var savesTab: SavesTab

    private fun List<Item>.containsUnFavorited(): Boolean =
        firstOrNull { it.favorite != true  } != null

    override fun onInitialized(items: List<Item>, savesTab: SavesTab) {
        this.savesTab = savesTab
        this.items = items
        _uiState.edit { copy(
            title = stringLoader.getQuantityString(R.plurals.lb_selected, items.size, items.size),
            favoriteText = if (items.containsUnFavorited()) {
                stringLoader.getString(com.pocket.ui.R.string.ic_favorite)
            } else {
                stringLoader.getString(com.pocket.ui.R.string.ic_unfavorite)
            }
        ) }
    }

    override fun onFavoriteClicked() {
        if (items.containsUnFavorited()) {
            itemRepository.favorite(*items.toTypedArray())
        } else {
            itemRepository.unFavorite(*items.toTypedArray())
        }
        _navigationEvents.tryEmit(BulkEditOverflowNavigationEvent.Close)
    }

    override fun onEditTagsClicked() {
        _navigationEvents.tryEmit(BulkEditOverflowNavigationEvent.OpenTagScreen)
    }

    override fun onMarkAsViewedClicked() {
        itemRepository.markAsViewed(*items.toTypedArray())
        _navigationEvents.tryEmit(BulkEditOverflowNavigationEvent.Close)
    }

    override fun onMarkAsNotViewedClicked() {
        itemRepository.markAsNotViewed(*items.toTypedArray())
        _navigationEvents.tryEmit(BulkEditOverflowNavigationEvent.Close)
    }
}

data class BulkEditOverflowBottomSheetUiState(
    val title: String = "",
    val favoriteText: String = ""
)

sealed class BulkEditOverflowNavigationEvent {
    object Close : BulkEditOverflowNavigationEvent()
    object OpenTagScreen: BulkEditOverflowNavigationEvent()
}

interface BulkEditOverflowBottomSheetInteractions {
    fun onInitialized(items: List<Item>, savesTab: SavesTab)
    fun onFavoriteClicked()
    fun onEditTagsClicked()
    fun onMarkAsViewedClicked()
    fun onMarkAsNotViewedClicked()
}