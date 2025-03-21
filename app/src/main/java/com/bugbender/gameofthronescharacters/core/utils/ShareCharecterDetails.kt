package com.bugbender.gameofthronescharacters.core.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import coil3.imageLoader

fun shareCharacter(
    context: Context,
    character: String,
    actor: String,
    debut: String,
    imageUrl: String
) {
    val shareText = buildString {
        appendLine("🔥 Discover $character from Game of Thrones!")
        appendLine("Played by: $actor")
        appendLine("Introduced in: $debut")
        appendLine("\n👉 Want to see more details, photos, and memorable moments?")
        appendLine("\nDownload the app here:")
        appendLine("https://play.google.com/store/apps/details?id=com.bugbender.gameofthronescharacters")
    }

    val diskCache = context.imageLoader.diskCache

    val snapshot = diskCache?.openSnapshot(imageUrl)

    if (snapshot != null) {
        val cachedFile = snapshot.data.toFile()

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            cachedFile
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    } else {
        val textIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(textIntent, "Share via"))
    }
}