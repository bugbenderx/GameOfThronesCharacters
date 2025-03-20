package com.bugbender.gameofthronescharacters.character.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.gameofthronescharacters.character.domain.CharacterRepository
import com.bugbender.gameofthronescharacters.character.domain.LoadResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Empty)
    val screenState: StateFlow<ScreenState> = _screenState

    init {
        loadRandomCharacter()
    }

    private var job: Job? = null

    fun loadRandomCharacter() {
        job?.cancel()
        job = viewModelScope.launch {
            _screenState.update { ScreenState.Loading }
            when (val result = repository.getRandom()) {
                is LoadResult.Success -> {
                    val characterUi = result.character.map(CharacterToUiMapper)

                    repository.getFavorite(id = result.character.id).collect {

                        _screenState.value =
                            ScreenState.Success(characterUi.copy(isFavorite = it != null))
                    }
                    _screenState.value = ScreenState.Success(characterUi)
                }

                is LoadResult.Error -> {
                    _screenState.value = ScreenState.Error(result.message, result.advice)
                }
            }
        }
    }

    fun changeFavoriteStatus(character: CharacterUi) {
        viewModelScope.launch {
            if (character.isFavorite) {
                repository.deleteFromFavorites(characterId = character.id)
            } else {
                repository.addToFavorites(character = character.toDomain())
            }
        }
    }

    sealed interface ScreenState {
        data object Loading : ScreenState
        data class Error(val message: String, val advice: String) : ScreenState
        data class Success(val character: CharacterUi) : ScreenState
        data object Empty : ScreenState
    }
}