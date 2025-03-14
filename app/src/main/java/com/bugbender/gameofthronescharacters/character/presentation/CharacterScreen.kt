package com.bugbender.gameofthronescharacters.character.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugbender.gameofthronescharacters.character.presentation.components.CharacterContent
import com.bugbender.gameofthronescharacters.character.presentation.components.CharacterErrorContent
import com.bugbender.gameofthronescharacters.character.presentation.components.CharacterLoadingContent

@Composable
fun CharacterScreen() {
    val viewModel = hiltViewModel<CharacterViewModel>()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    CharacterScreenContent(
        screenState,
        onRandomIconClick = viewModel::loadRandomCharacter,
    )
}

@Composable
fun CharacterScreenContent(
    screenState: CharacterViewModel.ScreenState,
    onRandomIconClick: () -> Unit,
) {
    when (screenState) {
        is CharacterViewModel.ScreenState.Loading -> {
            CharacterLoadingContent()
        }

        is CharacterViewModel.ScreenState.Error -> {
            CharacterErrorContent(
                errorMessage = screenState.message,
                advice = "Do nothing my friend!!!",
                onTryAgainButtonClick = onRandomIconClick
            )
        }

        is CharacterViewModel.ScreenState.Success -> {
            CharacterContent(
                characterUi = screenState.character,
                onRandomIconClick = onRandomIconClick,
                onShareIconClick = {},
                onFavoriteIconClick = {}
            )
        }

        is CharacterViewModel.ScreenState.Empty -> {}
    }
}

