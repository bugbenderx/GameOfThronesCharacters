package com.bugbender.gameofthronescharacters.favorites.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bugbender.gameofthronescharacters.favorites.presentation.components.FavoriteCharacterDetails

@Composable
fun FavoriteCharacterDetailsScreen(
    favoriteCharacterUi: FavoriteCharacterUi,
    navigateBack: () -> Unit
) {
    val viewModel = hiltViewModel<FavoriteCharacterDetailViewModel>()

    FavoriteCharacterDetails(
        characterUi = favoriteCharacterUi,
        onBackIconClick = navigateBack,
        onFavoriteIconClick = {
            viewModel.deleteFromFavorites(id = favoriteCharacterUi.id)
            navigateBack()
        }
    )
}