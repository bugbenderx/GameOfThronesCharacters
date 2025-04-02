package com.bugbender.gameofthronescharacters.character.presentation.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bugbender.gameofthronescharacters.R

@Composable
fun RandomFloatActionButton(onButtonClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = onButtonClick,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.random),
            contentDescription = stringResource(R.string.next_random_character_icon_button)
        )
    }
}