package com.bugbender.gameofthronescharacters.character.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ExpandingBackDrop(
    topBarHeight: Dp,
    topBarContent: @Composable () -> Unit,
    backLayerContent: @Composable () -> Unit,
    frontLayerContent: @Composable () -> Unit,
    frontLayerTopCornerRadius: Dp = 16.dp,
    frontLayerPaddingContent: Dp = 8.dp,
    enableAutoSnap: Boolean = true
) {
    val density = LocalDensity.current
    val topBarHeightPx = with(density) { topBarHeight.toPx() }
    val frontLayerTopCornerRadiusPx = with(density) { frontLayerTopCornerRadius.toPx() }
    val frontLayerPaddingContentPx = with(density) { frontLayerPaddingContent.toPx() }

    var backLayerHeightPx by remember { mutableIntStateOf(0) }

    val frontLayerMaxTopOffset by remember {
        derivedStateOf { backLayerHeightPx - frontLayerTopCornerRadiusPx }
    }
    val frontLayerOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        TopBar(
            content = topBarContent,
            modifier = Modifier
                .height(topBarHeight)
                .graphicsLayer {
                    val topBarVisibilityProgress = ((backLayerHeightPx - frontLayerOffset.value) /
                            (backLayerHeightPx - topBarHeightPx)).coerceIn(0f, 1f)
                    // Linear interpolation for translationY: fully hidden = -topBarHeightPx, fully visible = 0f.
                    translationY = -topBarHeightPx * (1 - topBarVisibilityProgress)
                    alpha = topBarVisibilityProgress
                }
        )

        BackLayer(
            content = backLayerContent,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    backLayerHeightPx = coordinates.size.height
                }
                .graphicsLayer {

                    val scrollValue = frontLayerMaxTopOffset - frontLayerOffset.value
                    translationY = -scrollValue
                    alpha = (-1f / (frontLayerMaxTopOffset - topBarHeightPx)) * scrollValue + 1
                }
        )

        FrontLayer(
            content = frontLayerContent,
            padding = frontLayerPaddingContent,
            topCornerRadius = frontLayerTopCornerRadius,
            modifier = Modifier
                .offset { IntOffset(0, frontLayerOffset.value.roundToInt()) }
                .draggable(
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            frontLayerOffset.snapTo(
                                (frontLayerOffset.value + delta).coerceIn(
                                    topBarHeightPx - frontLayerPaddingContentPx,
                                    backLayerHeightPx.toFloat() - frontLayerTopCornerRadiusPx
                                )
                            )
                        }
                    },
                    orientation = Orientation.Vertical,
                    onDragStopped = {
                        if (enableAutoSnap) {
                            coroutineScope.launch {
                                val target = if (frontLayerOffset.value < backLayerHeightPx / 2f)
                                    topBarHeightPx - frontLayerPaddingContentPx
                                else backLayerHeightPx.toFloat() - frontLayerTopCornerRadiusPx
                                frontLayerOffset.animateTo(target, tween(300))
                            }
                        }
                    }
                )
        )
    }

    LaunchedEffect(Unit) {
        // Set initial position at the bottom
        frontLayerOffset.snapTo(backLayerHeightPx.toFloat() - frontLayerTopCornerRadiusPx)
    }
}

@Composable
private fun TopBar(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
    ) {
        content()
    }
}

@Composable
private fun BackLayer(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        content()
    }

}

@Composable
private fun FrontLayer(
    content: @Composable () -> Unit,
    padding: Dp,
    topCornerRadius: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(
                    topStart = topCornerRadius,
                    topEnd = topCornerRadius
                )
            )
            .padding(padding)
    ) {
        content()
    }
}

@Preview
@Composable
private fun DraggableFrontLayerLayoutPreview() {
    GameOfThronesCharactersTheme {
        ExpandingBackDrop(
            topBarHeight = 72.dp,
            topBarContent = {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Jon Snow",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Played by Kit Harington",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            },
            backLayerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(16.dp)
                ) {
                    Text("Back Layer Content", fontSize = 24.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(200.dp)) // Simulating content
                }
            },
            frontLayerContent = {
                Text("Front Layer - Drag Me!", fontSize = 24.sp, color = Color.Black)
            }
        )
    }
}