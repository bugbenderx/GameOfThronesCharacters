package com.bugbender.gameofthronescharacters.settings.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme
import com.bugbender.gameofthronescharacters.settings.domain.ThemeMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ThemeCard(themeMode: ThemeMode, onThemeModeChange: (ThemeMode) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.theme),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )

        val light = stringResource(R.string.light)
        val dark = stringResource(R.string.dark)
        val system = stringResource(R.string.system)

        var selectedOption by remember {
            mutableStateOf(
                when (themeMode) {
                    ThemeMode.LIGHT -> light
                    ThemeMode.DARK -> dark
                    ThemeMode.SYSTEM -> system
                }
            )
        }
        val options = listOf(light, dark, system)

        val coroutineScope = rememberCoroutineScope()
        val animationDurationMillis = 500

        MultiSelector(
            options = options,
            selectedOption = selectedOption,
            onOptionSelect = { themeModeString ->
                selectedOption = themeModeString
                coroutineScope.launch {
                    delay(animationDurationMillis.toLong()) // Delay before changing theme
                    onThemeModeChange(
                        when (themeModeString) {
                            light -> ThemeMode.LIGHT
                            dark -> ThemeMode.DARK
                            else -> ThemeMode.SYSTEM
                        }
                    )
                }
            },
            animationDurationMillis = animationDurationMillis,
            modifier = Modifier
                .height(35.dp)
                .width(240.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ThemeCardPreview() {
    GameOfThronesCharactersTheme {
        ThemeCard(themeMode = ThemeMode.DARK, onThemeModeChange = {})
    }
}