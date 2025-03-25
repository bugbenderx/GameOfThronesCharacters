package com.bugbender.gameofthronescharacters.core.presentation.components.character

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AppIconButton(
    onClick: () -> Unit,
    @DrawableRes iconId: Int,
    @StringRes contentDescriptionId: Int,
    preventMultipleClicks: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isClickEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    IconButton(
        onClick = {
            onClick()
            if (preventMultipleClicks) {
                isClickEnabled = false
                coroutineScope.launch {
                    delay(1000L)
                    isClickEnabled = true
                }
            }
        },
        enabled = isClickEnabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        modifier = modifier.size(40.dp)
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = stringResource(contentDescriptionId)
        )
    }
}