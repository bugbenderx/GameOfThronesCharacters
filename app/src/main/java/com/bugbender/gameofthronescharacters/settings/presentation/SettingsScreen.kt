package com.bugbender.gameofthronescharacters.settings.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    val composeEmail: () -> Unit = {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // Only email apps handle this.
            putExtra(Intent.EXTRA_EMAIL, arrayOf("support@bugbender.com"))
            putExtra(Intent.EXTRA_SUBJECT, "GoT: Characters Feedback")
        }
        context.startActivity(intent)
    }

    val openPrivacyPolicy: () -> Unit = {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://sites.google.com/view/games-of-thrones-characters/home")
        )
        context.startActivity(intent)
    }

    SettingsContent(
        themeMode = themeMode,
        onThemeModeChange = viewModel::changeTheme,
        onContactSupportCardClick = composeEmail,
        onPrivacyPolicyCardClick = openPrivacyPolicy,
    )
}

@Composable
fun SettingsContent(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    onContactSupportCardClick: () -> Unit,
    onPrivacyPolicyCardClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .safeDrawingPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ThemeCard(themeMode = themeMode, onThemeModeChange = onThemeModeChange)
        Spacer(Modifier.weight(1f))
//        SettingsCard(
//            onCardClick = onRateAppCardClick,
//            iconRes = R.drawable.star,
//            titleRes = R.string.rate_app
//        )
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
            onContactSupportCardClick = { },
            onPrivacyPolicyCardClick = { }
        )
    }
}