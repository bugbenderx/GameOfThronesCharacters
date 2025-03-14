package com.bugbender.gameofthronescharacters.character.presentation.components.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme

@Composable
fun CharacterTopBar(character: String, actor: String) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = character,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = buildAnnotatedString {
                append("Played by ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(actor)
                }
            },
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun CharacterTopBarPreview() {
    GameOfThronesCharactersTheme {
        CharacterTopBar("Jon Snow", "Kit Harington")
    }
}