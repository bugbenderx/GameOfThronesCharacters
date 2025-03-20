package com.bugbender.gameofthronescharacters.favorites.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bugbender.gameofthronescharacters.favorites.presentation.components.FavoriteCharacterDetails

@Composable
fun FavoriteCharacterDetailsScreen(
    viewModel: FavoritesViewModel,
    favoriteCharacterUi: FavoriteCharacterUi,
    navigateBack: () -> Unit
) {
    val viewModel1 = hiltViewModel<FavoriteCharacterDetailViewModel>()

    FavoriteCharacterDetails(
        characterUi = favoriteCharacterUi,
        onBackIconClick = navigateBack,
        onFavoriteIconClick = {
            viewModel1.deleteFromFavorites(id = favoriteCharacterUi.id)
            navigateBack()
        }
    )
}