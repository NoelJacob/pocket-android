package com.pocket.app.home.details.slates

import androidx.lifecycle.viewModelScope
import com.pocket.app.home.details.DetailsViewModel
import com.pocket.app.home.details.toRecommendationUiState
import com.pocket.repository.HomeRepository
import com.pocket.repository.ItemRepository
import com.pocket.usecase.Save
import com.pocket.util.StringLoader
import com.pocket.util.edit
import com.pocket.util.java.Locale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlateDetailsViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    locale: Locale,
    private val stringLoader: StringLoader,
    itemRepository: ItemRepository,
    save: Save
) : DetailsViewModel(
    itemRepository = itemRepository,
    save = save,
) {

    private val localeString = locale.toString()

    fun onInitialized(index: Int) {
        setupSlateCollector(index)
        _uiState.edit {
            copy(
                screenState = ScreenState.Recommendations
            )
        }
    }

    private fun setupSlateCollector(index: Int) {
        viewModelScope.launch {
            homeRepository.getLineup(localeString).collect { lineup ->
                val slate = lineup.getOrNull(index)
                _uiState.edit {
                    copy(
                        title = slate?.title ?: "",
                        recommendations = slate?.recommendations?.map { recommendation ->
                            recommendation.toRecommendationUiState(stringLoader = stringLoader)
                        } ?: emptyList()
                    )
                }
            }
        }
    }

    override fun onErrorRetryClicked() {
        // we don't load data from network for slates, so error state is not possible
    }

    override fun onItemClicked(url: String, positionInList: Int, corpusRecommendationId: String?) {
        _events.tryEmit(Event.GoToReader(url))
    }

}
