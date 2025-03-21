package com.bugbender.gameofthronescharacters.character.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.character.presentation.CharacterUi
import com.bugbender.gameofthronescharacters.core.presentation.components.ExpandingBackDrop
import com.bugbender.gameofthronescharacters.core.presentation.components.character.AppIconButton
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterBackLayerContent
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterFrontLayerContent
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterRevealedContent
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterTabs
import com.bugbender.gameofthronescharacters.core.presentation.components.character.CharacterTopBar
import com.bugbender.gameofthronescharacters.core.utils.shareCharacter

@Composable
fun CharacterDetails(
    onRandomIconClick: () -> Unit,
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
                topLayer = {
                    AppIconButton(
                        onClick = onRandomIconClick,
                        iconId = R.drawable.random,
                        contentDescriptionId = R.string.next_random_character_icon_button
                    )
                },
                topLayerAlignment = Alignment.TopEnd
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