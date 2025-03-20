package com.bugbender.gameofthronescharacters.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteCharactersRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val screenState: StateFlow<ScreenState> = _screenState

    init {
        viewModelScope.launch {
            repository.getAllFavorites()
                .collect { list ->
                    if (list.isEmpty()) {
                        _screenState.value = ScreenState.Empty
                    } else {
                        _screenState.value = ScreenState.Characters(
                            characters = list.map { it.map(FavoriteDomainToUiMapper) }
                        )
                    }
                }
        }
    }

    sealed interface ScreenState {
        data object Empty : ScreenState
        data class Characters(val characters: List<FavoriteCharacterUi>) : ScreenState
    }
}

