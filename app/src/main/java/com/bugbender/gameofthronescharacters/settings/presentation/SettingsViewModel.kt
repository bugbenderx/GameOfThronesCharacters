package com.bugbender.gameofthronescharacters.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.gameofthronescharacters.settings.data.ThemeRepository
import com.bugbender.gameofthronescharacters.settings.domain.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : ViewModel() {

    val themeModeStateFlow: StateFlow<ThemeMode> = themeRepository.themeModeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ThemeMode.SYSTEM
    )

    fun changeTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            themeRepository.updateTheme(themeMode)
        }
    }
}