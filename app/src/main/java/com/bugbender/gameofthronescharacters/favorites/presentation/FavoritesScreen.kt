package com.bugbender.gameofthronescharacters.favorites.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme
import com.bugbender.gameofthronescharacters.favorites.presentation.FavoritesViewModel.ScreenState
import com.bugbender.gameofthronescharacters.favorites.presentation.components.FavoriteCharacters
import com.bugbender.gameofthronescharacters.favorites.presentation.components.NoFavorites

@Composable
fun FavoritesScreen(
    navigateToCharacterScreen: () -> Unit,
    navigateToFavoriteCharacterDetailsScreen: (FavoriteCharacterUi) -> Unit,
) {
    val viewModel = hiltViewModel<FavoritesViewModel>()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    FavoritesContent(
        screenState = screenState,
        onStartExploringButtonClick = navigateToCharacterScreen,
        onCharacterClick = { navigateToFavoriteCharacterDetailsScreen(it) }
    )
}

@Composable
fun FavoritesContent(
    screenState: ScreenState,
    onStartExploringButtonClick: () -> Unit,
    onCharacterClick: (FavoriteCharacterUi) -> Unit
) {
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.favorites),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )

        when (screenState) {
            is ScreenState.Empty -> {
                NoFavorites(onStartExploringButtonClick = onStartExploringButtonClick)
            }

            is ScreenState.Characters -> {
                FavoriteCharacters(
                    list = screenState.characters,
                    onCharacterClick = onCharacterClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun FavoritesContentPreview() {
    GameOfThronesCharactersTheme {
        FavoritesContent(
            screenState = ScreenState.Empty,
            onStartExploringButtonClick = {},
            onCharacterClick = {})
    }
}

