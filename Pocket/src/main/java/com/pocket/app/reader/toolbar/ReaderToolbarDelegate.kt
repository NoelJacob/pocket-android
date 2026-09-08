package com.pocket.app.reader.toolbar

import android.util.Log
import com.pocket.data.models.DomainItem
import com.pocket.repository.ArticleRepository
import com.pocket.repository.ItemRepository
import com.pocket.sdk.tts.toTrack
import com.pocket.usecase.Save
import com.pocket.util.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * For use within a view model that uses the ReaderToolbarView.
 * Contains a lot of interaction function logic
 */
open class ReaderToolbarDelegate(
    private val itemRepository: ItemRepository,
    private val articleRepository: ArticleRepository,
    private val save: Save,
    private val coroutineScope: CoroutineScope
) : ReaderToolbar.ToolbarInteractions,
    ReaderToolbar.ToolbarOverflowInteractions,
    ReaderToolbar.ToolbarUiStateHolder {

    lateinit var url: String

    protected val _toolbarEvents = MutableSharedFlow<ReaderToolbar.ToolbarEvent>(extraBufferCapacity = 1)
    val toolbarEvents: SharedFlow<ReaderToolbar.ToolbarEvent> = _toolbarEvents

    protected val _toolbarUiState = MutableStateFlow(ReaderToolbar.ToolbarUiState())
    override val toolbarUiState: StateFlow<ReaderToolbar.ToolbarUiState> = _toolbarUiState

    open suspend fun getToolbarOverflow(): ReaderToolbar.ToolbarOverflowUiState = ReaderToolbar.ToolbarOverflowUiState()

    override fun onUpClicked() {
        _toolbarEvents.tryEmit(ReaderToolbar.ToolbarEvent.GoBack)
    }

    override fun onSaveClicked() {
        coroutineScope.launch {
            when (save(url)) {
                Save.Result.Success -> {
                    _toolbarUiState.edit { copy(
                        actionButtonState = ReaderToolbar.ActionButtonState.Archive()
                    ) }
                }
                Save.Result.NotLoggedIn -> {
                    _toolbarEvents.emit(ReaderToolbar.ToolbarEvent.GoToSignIn)
                }
            }
        }
    }

    override fun onArchiveClicked() {
        itemRepository.archive(url)
        _toolbarEvents.tryEmit(ReaderToolbar.ToolbarEvent.GoBack)
    }

    override fun onReAddClicked() {
        itemRepository.unArchive(url)
        _toolbarUiState.edit { copy(
            actionButtonState = ReaderToolbar.ActionButtonState.Archive()
        ) }
    }

    override fun onListenClicked() {
        coroutineScope.launch {
            _toolbarEvents.tryEmit(
                ReaderToolbar.ToolbarEvent.OpenListen(getItem()?.toTrack())
            )
        }
    }

    override fun onShareClicked() {
        coroutineScope.launch {
            val item = getDomainItem()
            _toolbarEvents.tryEmit(ReaderToolbar.ToolbarEvent.Share(item?.displayTitle ?: ""))
        }
    }

    override fun onOverflowClicked() {
        coroutineScope.launch {
            _toolbarEvents.tryEmit(ReaderToolbar.ToolbarEvent.ShowOverflow(getToolbarOverflow()))
        }
    }

    override fun onTextSettingsClicked() {
    }

    override fun onViewOriginalClicked() {
    }

    override fun onRefreshClicked() {
    }

    override fun onFindInPageClicked() {
    }

    override fun onFavoriteClicked() {
        itemRepository.favorite(url)
    }

    override fun onUnfavoriteClicked() {
        itemRepository.unfavorite(url)
    }

    override fun onAddTagsClicked() {
        coroutineScope.launch {
            _toolbarEvents.tryEmit(ReaderToolbar.ToolbarEvent.ShowTagScreen(getItem()))
        }
    }

    override fun onHighlightsClicked() {
    }

    override fun onMarkAsNotViewedClicked() {
        itemRepository.markAsNotViewed(url)
        _toolbarEvents.tryEmit(ReaderToolbar.ToolbarEvent.GoBack)
    }

    override fun onDeleteClicked() {
        itemRepository.delete(url)
        _toolbarEvents.tryEmit(ReaderToolbar.ToolbarEvent.GoBack)
    }

    override fun onReportArticleClicked() {
        articleRepository.reportArticle(url)
        _toolbarEvents.tryEmit(ReaderToolbar.ToolbarEvent.ShowArticleReportedToast)
    }

    suspend fun getDomainItem(): DomainItem? =
        try {
            itemRepository.getDomainItem(url)
        } catch (e: Exception) {
            Log.e("Reader", e.message ?: "")
            null
        }

    private suspend fun getItem(): com.pocket.sdk.api.generated.thing.Item? =
        try {
            itemRepository.getItemOrThrow(url)
        } catch (e: Exception) {
            Log.e("Reader", e.message ?: "")
            null
        }
}