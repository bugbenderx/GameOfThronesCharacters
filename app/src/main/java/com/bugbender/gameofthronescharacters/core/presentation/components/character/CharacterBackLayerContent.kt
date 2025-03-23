package com.bugbender.gameofthronescharacters.core.presentation.components.character

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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme

@Composable
fun CharacterBackLayerContent(
    imageUrl: String,
    rank: Int,
    character: String,
    actor: String,
    debut: String,
    isFavorite: Boolean,
    onShareIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    topLayer: @Composable () -> Unit,
    topLayerAlignment: Alignment,
    topPadding: Dp,
    bottomPadding: Dp,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth()
        )
        // LinearGradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height)
                        ),
                        size = size
                    )
                }
        )

        Box(
            modifier = Modifier
                .align(topLayerAlignment)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(vertical = topPadding, horizontal = 16.dp)
        ) {
            topLayer()
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = bottomPadding
                )
        ) {
            Text(
                text = stringResource(R.string.rank, rank),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = character,
                color = Color.White,
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
                    color = Color.White,
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
                            append(debut)
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
            debut = "Season 1",
            isFavorite = false,
            onShareIconClick = {},
            onFavoriteIconClick = {},
            topLayer = {},
            topPadding = 16.dp,
            bottomPadding = 32.dp,
            topLayerAlignment = Alignment.Center
        )
    }
}