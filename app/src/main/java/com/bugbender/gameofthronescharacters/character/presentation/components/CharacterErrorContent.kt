package com.bugbender.gameofthronescharacters.character.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme

@Composable
fun CharacterErrorContent(
    onTryAgainButtonClick: () -> Unit,
    errorMessage: String,
    advice: String
) {
    Box(
        contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.error_background),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = advice,
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = onTryAgainButtonClick,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = stringResource(R.string.try_again),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun CharacterErrorContentPreview() {
    GameOfThronesCharactersTheme {
        CharacterErrorContent(
            onTryAgainButtonClick = {},
            errorMessage = "No internet connection",
            advice = "Your device  does not seem to have access to the internet."
        )
    }
}