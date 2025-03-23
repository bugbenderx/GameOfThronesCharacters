package com.bugbender.gameofthronescharacters.favorites.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.components.ExpandingBackDrop
import com.bugbender.gameofthronescharacters.core.presentation.components.character.AppIconButton
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterBackLayerContent
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterFrontLayerContent
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterRevealedContent
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterTabs
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterTopBar
import com.bugbender.gameofthronescharacters.core.presentation.theme.LocalWindowType
import com.bugbender.gameofthronescharacters.core.presentation.theme.WindowType
import com.bugbender.gameofthronescharacters.core.utils.shareCharacter
import com.bugbender.gameofthronescharacters.favorites.presentation.FavoriteCharacterUi

@Composable
fun FavoriteCharacterDetails(
    characterUi: FavoriteCharacterUi,
    onBackIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit
) {
    val context = LocalContext.current
    val onShareIconClick = {
        shareCharacter(
            context = context,
            character = characterUi.name,
            actor = characterUi.actor,
            debut = characterUi.debut,
            imageUrl = characterUi.imageUrl
        )
    }

    val windowType = LocalWindowType.current
    if (windowType == WindowType.Compact) {
        FavoriteCharacterDetailsCompact(
            onBackIconClick = onBackIconClick,
            onFavoriteIconClick = onFavoriteIconClick,
            onShareIconClick = onShareIconClick,
            favoriteCharacterUi = characterUi
        )
    } else {
        FavoriteCharacterDetailsExpanded(
            onBackIconClick = onBackIconClick,
            onFavoriteIconClick = onFavoriteIconClick,
            onShareIconClick = onShareIconClick,
            favoriteCharacterUi = characterUi
        )
    }
}

@Composable
fun FavoriteCharacterDetailsCompact(
    onBackIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    favoriteCharacterUi: FavoriteCharacterUi
) {
    ExpandingBackDrop(
        topBarContent = {
            CharacterTopBar(
                character = favoriteCharacterUi.name,
                actor = favoriteCharacterUi.actor
            )
        },
        backLayerContent = {
            CharacterBackLayerContent(
                imageUrl = favoriteCharacterUi.imageUrl,
                rank = favoriteCharacterUi.id,
                character = favoriteCharacterUi.name,
                actor = favoriteCharacterUi.actor,
                debut = favoriteCharacterUi.debut,
                isFavorite = true,
                onShareIconClick = onShareIconClick,
                onFavoriteIconClick = onFavoriteIconClick,
                topLayer = {
                    AppIconButton(
                        onClick = onBackIconClick,
                        iconId = R.drawable.back,
                        contentDescriptionId = R.string.back_icon_button
                    )
                },
                topLayerAlignment = Alignment.TopStart,
                topPadding = 16.dp,
                bottomPadding = 32.dp
            )
        },
        frontLayerContent = { isScrollEnabled ->
            CharacterFrontLayerContent(
                tabs = listOf(
                    CharacterTabs.Description(text = favoriteCharacterUi.description),
                    CharacterTabs.MemorableMoments(list = favoriteCharacterUi.memorableMoments)
                ),
                isScrollEnabled = isScrollEnabled
            )
        },
        revealedContent = {
            CharacterRevealedContent(
                onShareIconClick = onShareIconClick,
                onFavoriteIconClick = onFavoriteIconClick,
                isFavorite = true
            )
        }
    )
}

@Composable
fun FavoriteCharacterDetailsExpanded(
    onBackIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    favoriteCharacterUi: FavoriteCharacterUi
) {
    Row {
        CharacterBackLayerContent(
            imageUrl = favoriteCharacterUi.imageUrl,
            rank = favoriteCharacterUi.id,
            character = favoriteCharacterUi.name,
            actor = favoriteCharacterUi.actor,
            debut = favoriteCharacterUi.debut,
            isFavorite = true,
            onShareIconClick = onShareIconClick,
            onFavoriteIconClick = onFavoriteIconClick,
            topLayer = {
                AppIconButton(
                    onClick = onBackIconClick,
                    iconId = R.drawable.back,
                    contentDescriptionId = R.string.back_icon_button
                )
            },
            topLayerAlignment = Alignment.TopStart,
            topPadding = 0.dp,
            bottomPadding = 4.dp + WindowInsets.navigationBars.asPaddingValues()
                .calculateBottomPadding(),
            modifier = Modifier.weight(1f)
        )

        CharacterFrontLayerContent(
            tabs = listOf(
                CharacterTabs.Description(text = favoriteCharacterUi.description),
                CharacterTabs.MemorableMoments(list = favoriteCharacterUi.memorableMoments)
            ),
            isScrollEnabled = true,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 16.dp,
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    end = 8.dp + WindowInsets.navigationBars.asPaddingValues().calculateEndPadding(
                        LocalLayoutDirection.current
                    )
                )
        )
    }
}