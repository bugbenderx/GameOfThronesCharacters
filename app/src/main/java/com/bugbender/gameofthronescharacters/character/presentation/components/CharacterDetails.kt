package com.bugbender.gameofthronescharacters.character.presentation.components

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
import com.bugbender.gameofthronescharacters.character.presentation.CharacterUi
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

@Composable
fun CharacterDetails(
    onRandomButtonClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    characterUi: CharacterUi
) {

    val context = LocalContext.current
    val onShareIconClick: () -> Unit = {
        shareCharacter(
            context = context,
            character = characterUi.name,
            actor = characterUi.actor,
            debut = characterUi.debut,
            imageUrl = characterUi.imageUrl
        )
    }

    val windowType = LocalWindowType.current
    when (windowType) {
        WindowType.Compact -> {
            CharacterDetailsCompact(
                onRandomButtonClick = onRandomButtonClick,
                onFavoriteIconClick = onFavoriteIconClick,
                onShareIconClick = onShareIconClick,
                characterUi = characterUi
            )
        }

        WindowType.Expanded -> {
            CharacterDetailsExpanded(
                onRandomButtonClick = onRandomButtonClick,
                onFavoriteIconClick = onFavoriteIconClick,
                onShareIconClick = onShareIconClick,
                characterUi = characterUi
            )
        }
    }
}

@Composable
fun CharacterDetailsCompact(
    onRandomButtonClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    characterUi: CharacterUi
) {
    ExpandingBackDrop(
        topBarContent = {
            CharacterTopBar(
                character = characterUi.name,
                actor = characterUi.actor
            )
        },
        backLayerContent = {
            CharacterBackLayerContent(
                imageUrl = characterUi.imageUrl,
                rank = characterUi.id,
                character = characterUi.name,
                actor = characterUi.actor,
                debut = characterUi.debut,
                isFavorite = characterUi.isFavorite,
                onShareIconClick = onShareIconClick,
                onFavoriteIconClick = onFavoriteIconClick,

                bottomPadding = 32.dp,
            )
        },
        frontLayerContent = { isScrollEnabled ->
            CharacterFrontLayerContent(
                tabs = listOf(
                    CharacterTabs.Description(text = characterUi.description),
                    CharacterTabs.MemorableMoments(list = characterUi.memorableMoments)
                ),
                isScrollEnabled = isScrollEnabled
            )
        },
        floatActionButton = {
            RandomFloatActionButton(onButtonClick = onRandomButtonClick)
        },
        revealedContent = {
            CharacterRevealedContent(
                onShareIconClick = onShareIconClick,
                onFavoriteIconClick = onFavoriteIconClick,
                isFavorite = characterUi.isFavorite
            )
        }
    )
}

@Composable
fun CharacterDetailsExpanded(
    onRandomButtonClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    characterUi: CharacterUi
) {
    Row {
        CharacterBackLayerContent(
            imageUrl = characterUi.imageUrl,
            rank = characterUi.id,
            character = characterUi.name,
            actor = characterUi.actor,
            debut = characterUi.debut,
            isFavorite = characterUi.isFavorite,
            onShareIconClick = onShareIconClick,
            onFavoriteIconClick = onFavoriteIconClick,
            topLayer = {
                AppIconButton(
                    onClick = onRandomButtonClick,
                    iconId = R.drawable.random,
                    contentDescriptionId = R.string.next_random_character_icon_button
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
                CharacterTabs.Description(text = characterUi.description),
                CharacterTabs.MemorableMoments(list = characterUi.memorableMoments)
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