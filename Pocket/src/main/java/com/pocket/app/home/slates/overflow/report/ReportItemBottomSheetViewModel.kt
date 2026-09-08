package com.pocket.app.home.slates.overflow.report

import androidx.lifecycle.ViewModel
import com.pocket.util.edit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReportItemBottomSheetViewModel @Inject constructor(
): ViewModel(), ReportItemInteractions {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _events = MutableSharedFlow<Event>(extraBufferCapacity = 1)
    val events: SharedFlow<Event> = _events

    private lateinit var url: String
    private var corpusRecommendationId: String? = null

    private var otherText: String = ""

    override fun onInitialized(url: String, corpusRecommendationId: String?) {
        this.url = url
        this.corpusRecommendationId = corpusRecommendationId
    }

    override fun onBrokenClicked() {
        _uiState.edit { copy(
            submitButtonEnabled = true,
            reportReason = ReportReason.Broken()
        ) }
        _events.tryEmit(Event.HideKeyboard)
    }

    override fun onWrongCategoryClicked() {
        _uiState.edit { copy(
            submitButtonEnabled = true,
            reportReason = ReportReason.WrongCategory()
        ) }
        _events.tryEmit(Event.HideKeyboard)
    }

    override fun onSexuallyExplicitClicked() {
        _uiState.edit { copy(
            submitButtonEnabled = true,
            reportReason = ReportReason.SexuallyExplicit()
        ) }
        _events.tryEmit(Event.HideKeyboard)
    }

    override fun onOffensiveClicked() {
        _uiState.edit { copy(
            submitButtonEnabled = true,
            reportReason = ReportReason.Offensive()
        ) }
        _events.tryEmit(Event.HideKeyboard)
    }

    override fun onMisinformationClicked() {
        _uiState.edit { copy(
            submitButtonEnabled = true,
            reportReason = ReportReason.Misinformation()
        ) }
        _events.tryEmit(Event.HideKeyboard)
    }

    override fun onOtherClicked() {
        _uiState.edit { copy(
            submitButtonEnabled = true,
            reportReason = ReportReason.Other()
        ) }
    }

    override fun onOtherTextChanged(text: String) {
        otherText = text
    }

    override fun onSubmitClicked() {
        _events.tryEmit(Event.ShowToastAndClose)
    }

    data class UiState(
        val submitButtonEnabled: Boolean = false,
        val reportReason: ReportReason = ReportReason.None
    )

    sealed class ReportReason(
        val brokenSelected: Boolean = false,
        val wrongCategorySelected: Boolean = false,
        val sexuallyExplicitSelected: Boolean = false,
        val offensiveSelected: Boolean = false,
        val misinformationSelected: Boolean = false,
        val otherSelected: Boolean = false,
        val otherTextBoxVisible: Boolean = false,
    ) {
        object None: ReportReason()

        class Broken: ReportReason(
            brokenSelected = true,
        )

        class WrongCategory: ReportReason(
            wrongCategorySelected = true,
        )

        class SexuallyExplicit: ReportReason(
            sexuallyExplicitSelected = true,
        )

        class Offensive: ReportReason(
            offensiveSelected = true,
        )

        class Misinformation: ReportReason(
            misinformationSelected = true,
        )

        class Other: ReportReason(
            otherSelected = true,
            otherTextBoxVisible = true,
        )
    }

    sealed class Event {
        object ShowToastAndClose : Event()
        object HideKeyboard: Event()
    }
}

interface ReportItemInteractions {
    fun onInitialized(url: String, corpusRecommendationId: String?)
    fun onBrokenClicked()
    fun onWrongCategoryClicked()
    fun onSexuallyExplicitClicked()
    fun onOffensiveClicked()
    fun onMisinformationClicked()
    fun onOtherClicked()
    fun onOtherTextChanged(text: String)
    fun onSubmitClicked()
}