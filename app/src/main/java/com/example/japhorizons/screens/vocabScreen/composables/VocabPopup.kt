package com.example.japhorizons.screens.vocabScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.japhorizons.screens.vocabScreen.classes.Vocabulary

@Composable
fun VocabPopup(
    popupContents: Vocabulary,
    updateContents: (Vocabulary) -> Unit
) {
    Popup(
        alignment = Alignment.Center,
        properties = PopupProperties(focusable = true),
        onDismissRequest = {
            updateContents(
                Vocabulary(
                    kana = "",
                    romaji = "",
                    english = ""
                )
            )
        }
    ) {
        Box(
            Modifier
                .background(Color.Black)
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(15.dp)
                )
                .fillMaxWidth(0.7f)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                if (popupContents.kanji == null) {
                    PopupRow(title = "日本語", description = popupContents.kana)
                } else {
                    PopupRow(title = "かな", description = popupContents.kana)
                    PopupRow(title = "漢字", description = popupContents.kanji)
                }
                PopupRow(title = "Romaji", description = popupContents.romaji)
                PopupRow(title = "English", description = popupContents.english)
            }
        }
    }
}

@Composable
fun PopupRow(
    title: String,
    description: String
) {
    Row {
        Text(
            text = "$title:",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth(0.4f)
        )
        TextWithFurigana(
            text = description,
            color = Color.White
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}
