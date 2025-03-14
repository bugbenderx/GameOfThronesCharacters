package com.bugbender.gameofthronescharacters.character.presentation.components.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.imageLoader
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme

@Composable
fun CharacterBackLayerContent(
    imageUrl: String,
    rank: Int,
    character: String,
    actor: String,
    introducedIn: String,
    isFavorite: Boolean,
    onRandomIconClick: () -> Unit,
    onShareIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        imageLoader = LocalContext.current.imageLoader
    )

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSurface)
            .fillMaxSize()
            .paint(painter, contentScale = ContentScale.Crop)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
            )
    ) {

        AppIconButton(
            onClick = onRandomIconClick,
            iconId = R.drawable.random,
            contentDescriptionId = R.string.next_random_character_icon_button,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(16.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.rank, rank),
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = character,
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Played by\n")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(actor)
                        }
                    },
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                )
                AppIconButton(
                    onClick = onShareIconClick,
                    iconId = R.drawable.share,
                    contentDescriptionId = R.string.share_character_icon_button,
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Introduced in ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(introducedIn)
                        }
                    },
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 2.dp, horizontal = 8.dp)


                )
                Spacer(Modifier.weight(1f))
                AppIconButton(
                    onClick = onFavoriteIconClick,
                    iconId = if (isFavorite) R.drawable.star_filled else R.drawable.star,
                    contentDescriptionId = R.string.change_favorite_status_icon_button,
                )
            }
        }

    }
}

@Preview
@Composable
private fun CharacterBackLayerPreview() {
    GameOfThronesCharactersTheme {
        CharacterBackLayerContent(
            imageUrl = "TODO()",
            rank = 3,
            character = "Jon Snow",
            actor = "Kit Harington",
            introducedIn = "Season 1",
            isFavorite = false,
            onRandomIconClick = {},
            onShareIconClick = {},
            onFavoriteIconClick = {}
        )
    }
}