package com.bugbender.gameofthronescharacters.core.utils

import android.content.Context
import android.content.Intent

fun shareCharacter(context: Context, character: String, actor: String, debut: String) {
    val shareText = buildString {
        appendLine("🔥 Discover $character from Game of Thrones!")
        appendLine("Played by: $actor")
        appendLine("Introduced in: $debut")
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