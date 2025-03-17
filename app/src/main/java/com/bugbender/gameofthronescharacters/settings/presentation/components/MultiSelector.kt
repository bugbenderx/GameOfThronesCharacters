package com.bugbender.gameofthronescharacters.settings.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme
import com.bugbender.gameofthronescharacters.core.presentation.theme.fixedColorScheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


private const val AnimationDurationMillis = 500

@Stable
interface MultiSelectorState {
    val selectedIndex: Float
    val textColors: List<Color>

    fun selectOption(scope: CoroutineScope, index: Int)
}

@Stable
class MultiSelectorStateImpl(
    options: List<String>,
    selectedOption: String,
    private val selectedColor: Color,
    private val unselectedColor: Color,
) : MultiSelectorState {

    private var _selectedIndex = Animatable(options.indexOf(selectedOption).toFloat())

    private val numOptions = options.size
    private var _textColors: State<List<Color>> = derivedStateOf {
        List(numOptions) { index ->
            lerp(
                start = unselectedColor,
                stop = selectedColor,
                fraction = 1f - (((selectedIndex - index.toFloat()).absoluteValue).coerceAtMost(1f))
            )
        }
    }

    private val animationSpec = tween<Float>(
        durationMillis = AnimationDurationMillis,
        easing = FastOutSlowInEasing,
    )


    override val selectedIndex: Float get() = _selectedIndex.value

    override val textColors: List<Color>
        get() = _textColors.value

    override fun selectOption(scope: CoroutineScope, index: Int) {
        scope.launch {
            _selectedIndex.animateTo(
                targetValue = index.toFloat(),
                animationSpec = animationSpec,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiSelectorStateImpl

        if (selectedColor != other.selectedColor) return false
        if (unselectedColor != other.unselectedColor) return false
        if (_selectedIndex != other._selectedIndex) return false
        if (numOptions != other.numOptions) return false
        if (animationSpec != other.animationSpec) return false

        return true
    }

    override fun hashCode(): Int {
        var result = selectedColor.hashCode()
        result = 31 * result + unselectedColor.hashCode()
        result = 31 * result + _selectedIndex.hashCode()
        result = 31 * result + numOptions
        result = 31 * result + animationSpec.hashCode()
        return result
    }
}

@Composable
fun rememberMultiSelectorState(
    options: List<String>,
    selectedOption: String,
    selectedColor: Color,
    unSelectedColor: Color,
) = remember {
    MultiSelectorStateImpl(
        options,
        selectedOption,
        selectedColor,
        unSelectedColor,
    )
}

enum class MultiSelectorOption {
    Option,
    Background,
}

@Composable
fun MultiSelector(
    options: List<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    cornerRadius: Dp = 8.dp,
    selectedTextColor: Color = MaterialTheme.fixedColorScheme.surface,
    unselectedTextColor: Color = MaterialTheme.fixedColorScheme.onSurface,
    selectedBackgroundColor: Color = MaterialTheme.fixedColorScheme.onSurface,
    unselectedBackgroundColor: Color = MaterialTheme.fixedColorScheme.surface,
    state: MultiSelectorState = rememberMultiSelectorState(
        options = options,
        selectedOption = selectedOption,
        selectedColor = selectedTextColor,
        unSelectedColor = unselectedTextColor,
    ),
    modifier: Modifier = Modifier,
) {
    require(options.size >= 2) { "This composable requires at least 2 options" }
    require(options.contains(selectedOption)) { "Invalid selected option [$selectedOption]" }

    LaunchedEffect(key1 = options, key2 = selectedOption) {
        state.selectOption(this, options.indexOf(selectedOption))
    }

    Layout(
        modifier = modifier
            .clip(shape = RoundedCornerShape(cornerRadius))
            .background(unselectedBackgroundColor),
        content = {
            val colors = state.textColors
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .layoutId(MultiSelectorOption.Option)
                        .clickable { onOptionSelect(option) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.labelLarge,
                        color = colors[index],
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .layoutId(MultiSelectorOption.Background)
                    .clip(
                        shape = RoundedCornerShape(cornerRadius)
                    )
                    .background(selectedBackgroundColor),
            )
        }
    ) { measurables, constraints ->
        val optionWidth = constraints.maxWidth / options.size
        val optionConstraints = Constraints.fixed(
            width = optionWidth,
            height = constraints.maxHeight,
        )
        val optionPlaceables = measurables
            .filter { measurable -> measurable.layoutId == MultiSelectorOption.Option }
            .map { measurable -> measurable.measure(optionConstraints) }
        val backgroundPlaceable = measurables
            .first { measurable -> measurable.layoutId == MultiSelectorOption.Background }
            .measure(optionConstraints)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            backgroundPlaceable.placeRelative(
                x = (state.selectedIndex * optionWidth).toInt(),
                y = 0,
            )
            optionPlaceables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = optionWidth * index,
                    y = 0,
                )
            }
        }
    }
}

@Preview(widthDp = 412)
@Composable
fun PreviewMultiSelector() {
    GameOfThronesCharactersTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val options1 = listOf("Lorem", "Ipsum", "Dolor")
            var selectedOption1 by remember {
                mutableStateOf(options1.first())
            }
            val options2 = listOf("Sit", "Amet", "Consectetur", "Elit", "Quis")
            var selectedOption2 by remember {
                mutableStateOf(options2.first())
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MultiSelector(
                    options = options1,
                    selectedOption = selectedOption1,
                    onOptionSelect = { option ->
                        selectedOption1 = option
                    },
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                )

                MultiSelector(
                    options = options2,
                    selectedOption = selectedOption2,
                    onOptionSelect = { option ->
                        selectedOption2 = option
                    },
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MultiSelectorPreview() {
    GameOfThronesCharactersTheme {
        var selectedOption by remember { mutableStateOf("Light") }
        MultiSelector(
            options = listOf("Light", "Dark", "System"),
            selectedOption = selectedOption,
            onOptionSelect = { selectedOption = it },
            modifier = Modifier
                .height(28.dp)
                .width(210.dp)
        )
    }
}