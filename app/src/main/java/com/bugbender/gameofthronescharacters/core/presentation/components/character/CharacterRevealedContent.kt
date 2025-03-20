package com.bugbender.gameofthronescharacters.core.presentation.components.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bugbender.gameofthronescharacters.R

@Composable
fun CharacterRevealedContent(
    onShareIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    isFavorite: Boolean
) {
    Column(modifier = Modifier.padding(16.dp)) {
        AppIconButton(
            onClick = onShareIconClick,
            iconId = R.drawable.share,
            contentDescriptionId = R.string.share_character_icon_button,
        )
        Spacer(Modifier.height(8.dp))
        AppIconButton(
            onClick = onFavoriteIconClick,
            iconId = if (isFavorite) R.drawable.star_filled else R.drawable.star,
            contentDescriptionId = R.string.change_favorite_status_icon_button,
        )
    }
}