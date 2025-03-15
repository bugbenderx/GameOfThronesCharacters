package com.bugbender.gameofthronescharacters.character.presentation.components

import androidx.compose.runtime.Composable
import com.bugbender.gameofthronescharacters.character.presentation.CharacterUi
import com.bugbender.gameofthronescharacters.character.presentation.components.character.CharacterBackLayerContent
import com.bugbender.gameofthronescharacters.character.presentation.components.character.CharacterFrontLayerContent
import com.bugbender.gameofthronescharacters.character.presentation.components.character.CharacterRevealedContent
import com.bugbender.gameofthronescharacters.character.presentation.components.character.CharacterTabs
import com.bugbender.gameofthronescharacters.character.presentation.components.character.CharacterTopBar
import com.bugbender.gameofthronescharacters.character.presentation.components.character.ExpandingBackDrop

@Composable
fun CharacterContent(
    onRandomIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
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
                introducedIn = characterUi.debut,
                isFavorite = characterUi.isFavorite,
                onRandomIconClick = onRandomIconClick,
                onShareIconClick = onShareIconClick,
                onFavoriteIconClick = onFavoriteIconClick
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
        revealedContent = {
            CharacterRevealedContent(
                onShareIconClick = onShareIconClick,
                onFavoriteIconClick = onFavoriteIconClick,
                isFavorite = characterUi.isFavorite
            )
        }
    )
}