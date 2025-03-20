package com.bugbender.gameofthronescharacters.character.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugbender.gameofthronescharacters.character.presentation.components.CharacterDetails
import com.bugbender.gameofthronescharacters.character.presentation.components.CharacterErrorContent
import com.bugbender.gameofthronescharacters.character.presentation.components.CharacterLoadingContent

@Composable
fun CharacterScreen() {
    val viewModel = hiltViewModel<CharacterViewModel>()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    CharacterScreenContent(
        screenState = screenState,
        onRandomIconClick = viewModel::loadRandomCharacter,
        onFavoriteIconClick = viewModel::changeFavoriteStatus
    )
}

@Composable
fun CharacterScreenContent(
    screenState: CharacterViewModel.ScreenState,
    onRandomIconClick: () -> Unit,
    onFavoriteIconClick: (CharacterUi) -> Unit
) {
    when (screenState) {

        is CharacterViewModel.ScreenState.Error -> {
            CharacterErrorContent(
                errorMessage = screenState.message,
                advice = screenState.advice,
                onTryAgainButtonClick = onRandomIconClick
            )
        }

        is CharacterViewModel.ScreenState.Success -> {
            CharacterDetails(
                characterUi = screenState.character,
                onRandomIconClick = onRandomIconClick,
                onFavoriteIconClick = { onFavoriteIconClick(screenState.character) }
            )
        }

        else -> {
            CharacterLoadingContent()
        }
    }
}