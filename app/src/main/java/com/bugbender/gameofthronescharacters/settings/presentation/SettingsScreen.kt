package com.bugbender.gameofthronescharacters.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme
import com.bugbender.gameofthronescharacters.settings.domain.ThemeMode
import com.bugbender.gameofthronescharacters.settings.presentation.components.SettingsCard
import com.bugbender.gameofthronescharacters.settings.presentation.components.ThemeCard

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val themeMode by viewModel.themeModeStateFlow.collectAsStateWithLifecycle()

    SettingsContent(
        themeMode = themeMode,
        onThemeModeChange = viewModel::changeTheme,
        onRateAppCardClick = { },
        onContactSupportCardClick = { },
        onPrivacyPolicyCardClick = {},
    )
}

@Composable
fun SettingsContent(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    onRateAppCardClick: () -> Unit,
    onContactSupportCardClick: () -> Unit,
    onPrivacyPolicyCardClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        ThemeCard(themeMode = themeMode, onThemeModeChange = onThemeModeChange)
        Spacer(Modifier.weight(1f))
        SettingsCard(
            onCardClick = onRateAppCardClick,
            iconRes = R.drawable.star,
            titleRes = R.string.rate_app
        )
        SettingsCard(
            onCardClick = onContactSupportCardClick,
            iconRes = R.drawable.support,
            titleRes = R.string.contact_support
        )
        SettingsCard(
            onCardClick = onPrivacyPolicyCardClick,
            iconRes = R.drawable.privacy_policy,
            titleRes = R.string.privacy_policy
        )
    }
}

@Preview
@Composable
private fun SettingsContentPreview() {
    GameOfThronesCharactersTheme {
        SettingsContent(
            themeMode = ThemeMode.DARK,
            onThemeModeChange = {},
            onRateAppCardClick = { },
            onContactSupportCardClick = { },
            onPrivacyPolicyCardClick = { }
        )
    }
}