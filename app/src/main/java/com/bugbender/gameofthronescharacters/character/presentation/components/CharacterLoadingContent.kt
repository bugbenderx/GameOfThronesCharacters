package com.bugbender.gameofthronescharacters.character.presentation.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.components.ExpandingBackDrop
import com.bugbender.gameofthronescharacters.core.presentation.theme.LocalWindowType
import com.bugbender.gameofthronescharacters.core.presentation.theme.WindowType

@Composable
fun CharacterLoadingContent() {
    val windowType = LocalWindowType.current
    when (windowType) {
        WindowType.Compact -> {
            CharacterLoadingCompact()
        }

        WindowType.Expanded -> {
            CharacterLoadingExpanded()
        }
    }
}

@Composable
fun CharacterLoadingExpanded() {
    Row(
        modifier = Modifier

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect()
                .weight(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.character),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .safeDrawingPadding()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(28.dp)
                    .fillMaxWidth(0.5f)
                    .shimmerEffect()
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            repeat(10) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .height(16.dp)
                        .fillMaxWidth()
                        .shimmerEffect()
                )
            }
        }
    }
}


@Composable
fun CharacterLoadingCompact() {
    ExpandingBackDrop(topBarContent = {}, backLayerContent = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect()
        ) {
            Icon(
                painter = painterResource(R.drawable.character),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center)
            )
        }
    }, frontLayerContent = {
        Column {
            Box(
                modifier = Modifier
                    .height(28.dp)
                    .fillMaxWidth(0.5f)
                    .shimmerEffect()
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            repeat(10) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .height(16.dp)
                        .fillMaxWidth()
                        .shimmerEffect()
                )
            }
        }
    }, revealedContent = {})
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.outlineVariant,
                MaterialTheme.colorScheme.outline,
                MaterialTheme.colorScheme.outlineVariant,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}