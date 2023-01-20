package com.example.japhorizons.screens.vocabScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.japhorizons.screens.vocabScreen.classes.TextWithFuriganaData

@Composable
fun TextWithFurigana(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.White,
    showFurigana: Boolean = true
) {
    val textdata = text.split("｛", "｝").mapIndexed { i, dataItem ->
        if (i % 2 == 0) TextWithFuriganaData(text = dataItem)
        else {
            val (k, f) = dataItem.split("：")
            if (showFurigana) TextWithFuriganaData(text = k, furigana = f)
            else TextWithFuriganaData(text = k)
        }
    }
    val (newText, inlineContent) = remember(textdata) {
        calculateAnnotatedString(
            textContent = textdata,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            showFurigana = showFurigana
        )
    }
    Text(
        text = newText,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        inlineContent = inlineContent
    )
}

fun calculateAnnotatedString(
    textContent: List<TextWithFuriganaData>,
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    showFurigana: Boolean
): Pair<AnnotatedString, Map<String, InlineTextContent>> {
    val inlineContent = mutableMapOf<String, InlineTextContent>()

    return buildAnnotatedString {
        for (elem in textContent) {
            val kanji = elem.text
            val furigana = elem.furigana

            if (furigana == null) {
                append(text = kanji)
                continue
            }

            val width = fontSize * maxOf(furigana.length * 0.525f, kanji.length * 1.07f)
            appendInlineContent(kanji, kanji)
            inlineContent[kanji] = InlineTextContent(
                placeholder = Placeholder(
                    // where do these magic numbers come from? :/
                    width = width,
                    height = fontSize * (if (showFurigana) 2.05f else 1.5f),
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Bottom,
                ),
                children = {
                    MergeFurigana(
                        kanji = kanji,
                        furigana = furigana,
                        color = color,
                        fontSize = fontSize,
                        fontWeight = fontWeight
                    )
                }
            )
        }
    } to inlineContent
}


@Composable
fun MergeFurigana(
    kanji: String,
    furigana: String,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = furigana,
            fontSize = fontSize / 2,
            fontWeight = fontWeight,
            color = color,
            modifier = Modifier.offset(0.dp, 2.dp)
        )
        Text(
            text = kanji,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            textAlign = TextAlign.Center
        )
    }
}

fun separateFuriganaAndKanji(text: String): Pair<String, String> {
    return Pair(
        text.split("｛", "｝").mapIndexed { i, dataItem ->
            if (i % 2 == 0) dataItem
            else {
                val (k, _) = dataItem.split("：")
                k
            }
        }.joinToString(separator = ""),
        text.split("｛", "｝").mapIndexed { i, dataItem ->
            if (i % 2 == 0) dataItem
            else {
                val (_, f) = dataItem.split("：")
                f
            }
        }.joinToString(separator = "")
    )
}
