package com.example.japhorizons.screens.vocabScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.japhorizons.screens.vocabScreen.classes.CollapsableSection
import com.example.japhorizons.screens.vocabScreen.classes.Vocabulary
import java.util.*

@Composable
fun CollapsableLazyList(
    modifier: Modifier = Modifier,
    sections: List<CollapsableSection>,
    primaryLanguage: Int,
    secondaryLanguage: Int,
    searchValue: String,
    updatePopupContents: (Vocabulary) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val collapsedState = remember(sections) { sections.map { true }.toMutableStateList() }
    LazyColumn(modifier) {
        sections.forEachIndexed { i, dataItem ->
            val collapsed = collapsedState[i]
            val rowsToDisplay = dataItem.rows.filter { r -> vocabContains(r, searchValue) }
            item(key = "header_$i") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            collapsedState[i] = !collapsed
                            focusManager.clearFocus()
                        }
                ) {
                    Icon(
                        Icons.Default.run {
                            if (collapsed)
                                KeyboardArrowDown
                            else
                                KeyboardArrowUp
                        },
                        contentDescription = "",
                        tint = Color.Gray,
                    )
                    Text(
                        text = dataItem.title,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                    )
                    if (searchValue != "") {
                        Text(
                            text = "  ~ ${rowsToDisplay.size} results",
                            fontWeight = FontWeight.Light,
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        )
                    }
                }
                Divider(color = Color.Gray)
            }
            if (!collapsed) {
                items(rowsToDisplay) { row ->
                    Column(
                        modifier = Modifier.clickable {
                            updatePopupContents(row)
                            focusManager.clearFocus()
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MaterialIconDimension.dp)
                        ) {
                            TextWithFurigana(
                                text = indexToLanguage(row, primaryLanguage),
                                color = Color.White,
                                showFurigana = primaryLanguage == 0,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MaterialIconDimension.dp)
                        ) {
                            TextWithFurigana(
                                text = indexToLanguage(row, secondaryLanguage),
                                color = Color.Gray,
                                showFurigana = secondaryLanguage == 0,
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                            )
                        }
                        if (row.endOfSection) Divider(color = Color.Gray)
                        else Divider(color = Color.DarkGray)
                    }
                }
            }
        }
    }
}

fun indexToLanguage(
    vocab: Vocabulary,
    index: Int
): String {
    return if (index == 1 && vocab.kanji != null) vocab.kanji
    else if (index == 2) vocab.romaji
    else if (index == 3) vocab.english
    else vocab.kana
}

const val MaterialIconDimension = 24f

fun vocabContains(vocab: Vocabulary, value: String): Boolean {
    val cleanString = { s: String -> s.filter { it.isLetterOrDigit() }.lowercase(Locale.ROOT) }
    val cleanValue = cleanString(value)

    return if (vocab.kanji == null) {
        val (kan, fur) = separateFuriganaAndKanji(vocab.kana)
        cleanString(kan).contains(cleanValue) || cleanString(fur).contains(cleanValue)
    } else {
        cleanString(vocab.kanji).contains(cleanValue)
    }
    || cleanString(vocab.romaji).contains(cleanValue)
    || cleanString(vocab.english).contains(cleanValue)
}
