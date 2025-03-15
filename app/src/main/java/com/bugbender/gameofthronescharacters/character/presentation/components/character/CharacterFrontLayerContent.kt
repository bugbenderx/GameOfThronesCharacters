package com.bugbender.gameofthronescharacters.character.presentation.components.character

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bugbender.gameofthronescharacters.R
import com.bugbender.gameofthronescharacters.core.presentation.theme.GameOfThronesCharactersTheme
import kotlinx.coroutines.launch

sealed class CharacterTabs(@StringRes val textId: Int) {
    class Description(val text: String) : CharacterTabs(R.string.description)
    class MemorableMoments(val list: List<String>) : CharacterTabs(R.string.memorableMoments)
}

@Composable
fun CharacterFrontLayerContent(
    tabs: List<CharacterTabs>,
    isScrollEnabled: Boolean,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }

    var availableHeightPx by remember { mutableIntStateOf(0) }
    var contentFullHeightPx by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {

            tabs.forEachIndexed { index, currentTab ->
                val (tabColor, tabFontWeight) = if (tabs[selectedTabIndex] == currentTab)
                    Pair(MaterialTheme.colorScheme.onSurface, FontWeight.Bold)
                else Pair(MaterialTheme.colorScheme.outline, FontWeight.Normal)

                Text(
                    text = stringResource(currentTab.textId),
                    color = tabColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = tabFontWeight,
                    modifier = Modifier.clickable(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                )
                Spacer(Modifier.width(16.dp))
            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    availableHeightPx = coordinates.size.height
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        contentFullHeightPx = maxOf(contentFullHeightPx, coordinates.size.height)
                    }
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = isScrollEnabled && (contentFullHeightPx > availableHeightPx)
                    )
            ) {
                when (val tab = tabs[selectedTabIndex]) {

                    is CharacterTabs.Description -> {
                        Text(
                            text = tab.text,
                            color = MaterialTheme.colorScheme.outline,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }

                    is CharacterTabs.MemorableMoments -> {
                        tab.list.forEachIndexed { index, text ->
                            Text(
                                text = text,
                                color = MaterialTheme.colorScheme.outline,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            if (index != tab.list.lastIndex)
                                Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CharacterFrontLayerPreview() {
    GameOfThronesCharactersTheme {
        CharacterFrontLayerContent(
            tabs = listOf(
                CharacterTabs.Description(text = "Jon Snow stands out as one of Game of Thrones' most compelling characters, embodying classic heroism with his unwavering honor, kindness, intelligence, persistence, and selflessness. Time and again, he prioritizes duty over personal desires, demonstrating true leadership.\\n\\nIn a tragic twist, Jon is killed in a mutiny by men he once commanded. His allies resort to magic in desperation to bring him back, underscoring his importance as both a good man and their leader.\\n\\nBeyond his role in the Night’s Watch, Jon's mysterious parentage adds depth to his character. Known initially as Ned Stark's illegitimate son, Jon has shown little interest in claiming the Iron Throne. However, should he discover a legitimate claim to it, along with the support of the North as \\\"King in the North,\\\" it could lead to fascinating developments, especially when encountering Daenerys and her dragons."),
                CharacterTabs.MemorableMoments(
                    list = listOf(
                        "Discovering his white direwolf pup, Ghost, and feeling an instant connection.",
                        "Choosing to remain Lord Commander of the Night’s Watch over being legitimized as a Stark.",
                        "Being resurrected by Melisandre in Season 6, surprising everyone who believed him dead."
                    )
                )
            ),
            isScrollEnabled = true
        )
    }
}