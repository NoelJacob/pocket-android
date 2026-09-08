package com.pocket.app.settings.appicon

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppIconSettingsViewModel
@Inject constructor(
    private val appIcons: AppIcons
) : ViewModel() {
    fun onViewShown() {
    }

    private val currentIcon = appIcons.current
    val automaticIcon = appIcons.automatic.toAppIconUiState()
    val allIcons = appIcons.all.map { it.toAppIconUiState() }

    fun onAutomaticIconClick() {
        appIcons.current = appIcons.automatic
    }

    fun onIconClick(index: Int) {
        val appIcon = appIcons.all[index]
        appIcons.current = appIcon
    }

    private fun AppIconOption.toAppIconUiState() = AppIconUiState(
        appIcons.loadIcon(this),
        label,
        currentIcon == this
    )
}
