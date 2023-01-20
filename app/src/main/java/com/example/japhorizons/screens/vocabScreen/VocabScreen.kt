package com.example.japhorizons.screens.vocabScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.japhorizons.screens.vocabScreen.classes.CollapsableSection
import com.example.japhorizons.screens.vocabScreen.classes.Vocabulary
import com.example.japhorizons.screens.vocabScreen.composables.*

@Composable
fun VocabScreen() {
    var primaryLanguage by remember {
        mutableStateOf(0)
    }
    var secondaryLanguage by remember {
        mutableStateOf(3)
    }
    var popupContents by remember {
        mutableStateOf(
            Vocabulary(
                kana = "",
                romaji = "",
                english = ""
            )
        )
    }
    var searchValue by remember {
        mutableStateOf("")
    }
    val jsonData = readVocabFromJson()

    if (popupContents.kana != "") {
        VocabPopup(popupContents = popupContents) {
            popupContents = it
        }
    }
    Scaffold(
        backgroundColor = Color.Black,
        topBar = {
            Column {
                Row {
                    LanguageToggle(
                        name = "Primary",
                        selected = primaryLanguage,
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        primaryLanguage = it
                    }
                    LanguageToggle(
                        name = "Secondary",
                        selected = secondaryLanguage
                    ) {
                        secondaryLanguage = it
                    }
                }
                SearchBar(value = searchValue) {
                    searchValue = it
                }
            }
        }
    ) { padding ->
        CollapsableLazyList(
            modifier = Modifier.padding(padding),
            primaryLanguage = primaryLanguage,
            secondaryLanguage = secondaryLanguage,
            searchValue = searchValue,
            sections = jsonData.chapters.map {
                CollapsableSection(
                    title = it.chapter,
                    rows = it.vocabs
                )
            }
        ) {
            popupContents = it
        }
    }
}
