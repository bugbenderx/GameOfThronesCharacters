package com.bugbender.gameofthronescharacters.character.presentation.components.character

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.ui.Alignment
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
    topBarContent: @Composable () -> Unit,
    backLayerContent: @Composable () -> Unit,
    backLayerHeightFraction: Float = 0.8f,
    frontLayerContent: @Composable (Boolean) -> Unit,
    frontLayerTopCornerRadius: Dp = 16.dp,
    frontLayerPaddingContent: Dp = 8.dp,
    enableAutoSnap: Boolean = true,
    revealedContent: @Composable () -> Unit,
) {
    var parentHeightPx by remember { mutableIntStateOf(0) }
    var topBarHeightPx by remember { mutableIntStateOf(0) }
    var backLayerHeightPx by remember { mutableIntStateOf(0) }
    val isBackLayerDrawn by remember { derivedStateOf { backLayerHeightPx > 0 } }

    val density = LocalDensity.current
    val frontLayerTopCornerRadiusPx = with(density) { frontLayerTopCornerRadius.toPx() }
    val frontLayerPaddingContentPx = with(density) { frontLayerPaddingContent.toPx() }

    val frontLayerMinTopOffset by remember {
        derivedStateOf { (topBarHeightPx - frontLayerPaddingContentPx).let { if (it < 0f) 0f else it } }
    }
    val frontLayerMaxTopOffset by remember {
        derivedStateOf { backLayerHeightPx - frontLayerTopCornerRadiusPx }
    }
    val frontLayerMaxHeight by remember {
        derivedStateOf { with(density) { (parentHeightPx - frontLayerMinTopOffset).toDp() } }
    }
    val frontLayerOffset = remember { Animatable(0f) }

    val isFrontLayerScrollEnabled by remember {
        derivedStateOf { frontLayerOffset.value == frontLayerMinTopOffset }
    }

    val showRevealedContent by remember {
        derivedStateOf { frontLayerMinTopOffset + frontLayerPaddingContentPx > frontLayerOffset.value }
    }

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .onGloballyPositioned { coordinates ->
                parentHeightPx = coordinates.size.height
            }
    ) {

        TopBar(
            readyToShow = isBackLayerDrawn,
            content = topBarContent,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    topBarHeightPx = coordinates.size.height
                }
                .graphicsLayer {
                    val topBarVisibilityProgress =
                        ((backLayerHeightPx - frontLayerOffset.value) /
                                (backLayerHeightPx - topBarHeightPx)).coerceIn(0f, 1f)
                    // Linear interpolation for translationY: fully hidden = -topBarHeightPx, fully visible = 0f.
                    translationY = -topBarHeightPx * (1 - topBarVisibilityProgress)
                    alpha = topBarVisibilityProgress
                }
        )

        BackLayer(
            content = backLayerContent,
            modifier = Modifier
                .fillMaxHeight(backLayerHeightFraction)
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
            readyToShow = isBackLayerDrawn,
            content = { frontLayerContent(isFrontLayerScrollEnabled) },
            paddingContent = frontLayerPaddingContent,
            topCornerRadius = frontLayerTopCornerRadius,
            modifier = Modifier
                .heightIn(max = frontLayerMaxHeight)
                .offset { IntOffset(0, frontLayerOffset.value.roundToInt()) }
                .draggable(
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            frontLayerOffset.snapTo(
                                (frontLayerOffset.value + delta).coerceIn(
                                    frontLayerMinTopOffset,
                                    backLayerHeightPx.toFloat() - frontLayerTopCornerRadiusPx
                                )
                            )
                        }
                    },
                    orientation = Orientation.Vertical,
                    onDragStopped = {
                        if (enableAutoSnap) {
                            coroutineScope.launch {
                                val target =
                                    if (frontLayerOffset.value < backLayerHeightPx / 2f)
                                        frontLayerMinTopOffset
                                    else
                                        frontLayerMaxTopOffset
                                frontLayerOffset.animateTo(target, tween(300))
                            }
                        }
                    }
                )
        )

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomEnd),
            visible = showRevealedContent,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            revealedContent()
        }
    }

    LaunchedEffect(Unit) {
        // Set initial frontLayer position at the bottom
        frontLayerOffset.snapTo(frontLayerMaxTopOffset)
    }
}

@Composable
private fun TopBar(
    readyToShow: Boolean,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (readyToShow) {
        Box(
            modifier = modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .fillMaxWidth()
                .zIndex(1f)
        ) {
            content()
        }
    }
}

@Composable
private fun BackLayer(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        content()
    }
}

@Composable
private fun FrontLayer(
    readyToShow: Boolean,
    content: @Composable () -> Unit,
    paddingContent: Dp,
    topCornerRadius: Dp,
    modifier: Modifier = Modifier
) {
    if (readyToShow) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(
                        topStart = topCornerRadius,
                        topEnd = topCornerRadius
                    )
                )
                .padding(paddingContent)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun DraggableFrontLayerLayoutPreview() {
    GameOfThronesCharactersTheme {
        ExpandingBackDrop(
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
            },
            revealedContent = {}
        )
    }
}