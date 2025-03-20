package com.bugbender.gameofthronescharacters.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteCharacterDetailViewModel @Inject constructor(
    private val repository: FavoriteCharactersRepository
) : ViewModel() {

    fun deleteFromFavorites(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}