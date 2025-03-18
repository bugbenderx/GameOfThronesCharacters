package com.bugbender.gameofthronescharacters.character.presentation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugbender.gameofthronescharacters.character.presentation.components.CharacterContent
import com.bugbender.gameofthronescharacters.character.presentation.components.CharacterErrorContent
import com.bugbender.gameofthronescharacters.character.presentation.components.CharacterLoadingContent

@Composable
fun CharacterScreen() {
    val viewModel = hiltViewModel<CharacterViewModel>()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val shareCharacter: (CharacterUi) -> Unit = { character ->
        val shareText = buildString {
            appendLine("🔥 Discover ${character.name} from Game of Thrones!")
            appendLine("Played by: ${character.actor}")
            appendLine("Introduced in: ${character.debut}")
            appendLine("\n👉 Want to see more details, photos, and memorable moments?")
            appendLine("Download the app here:")
            appendLine("https://play.google.com/store/apps/details?id=com.bugbender.gameofthronescharacters")
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }

    CharacterScreenContent(
        screenState = screenState,
        onRandomIconClick = viewModel::loadRandomCharacter,
        onShareIconClick = shareCharacter
    )
}

@Composable
fun CharacterScreenContent(
    screenState: CharacterViewModel.ScreenState,
    onRandomIconClick: () -> Unit,
    onShareIconClick: (CharacterUi) -> Unit
) {
    when (screenState) {
        is CharacterViewModel.ScreenState.Loading -> {
            CharacterLoadingContent()
        }

        is CharacterViewModel.ScreenState.Error -> {
            CharacterErrorContent(
                errorMessage = screenState.message,
                advice = screenState.advice,
                onTryAgainButtonClick = onRandomIconClick
            )
        }

        is CharacterViewModel.ScreenState.Success -> {
            CharacterContent(
                characterUi = screenState.character,
                onRandomIconClick = onRandomIconClick,
                onShareIconClick = { onShareIconClick(screenState.character) },
                onFavoriteIconClick = {}
            )
        }

        is CharacterViewModel.ScreenState.Empty -> {}
    }
}

