package com.bugbender.gameofthronescharacters.settings.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme

@Composable
fun SettingsCard(
    onCardClick: () -> Unit,
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int,
    dividerColor: Color = MaterialTheme.colorScheme.outlineVariant
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onCardClick)
            .height(60.dp)
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 0.5.dp.toPx()
                val y = size.height - strokeWidth / 2

                drawLine(
                    color = dividerColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = stringResource(titleRes),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
private fun SettingsCardPreview() {
    GameOfThronesCharactersTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            SettingsCard(
                onCardClick = {},
                iconRes = R.drawable.star,
                titleRes = R.string.rate_app
            )
            SettingsCard(
                onCardClick = {},
                iconRes = R.drawable.support,
                titleRes = R.string.contact_support
            )
            SettingsCard(
                onCardClick = {},
                iconRes = R.drawable.privacy_policy,
                titleRes = R.string.privacy_policy
            )
        }
    }
}