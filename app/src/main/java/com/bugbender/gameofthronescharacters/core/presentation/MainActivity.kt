package com.bugbender.gameofthronescharacters.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameOfThronesCharactersTheme {
                GameOfThronesCharactersApp(it)
            }
        }
    }
}