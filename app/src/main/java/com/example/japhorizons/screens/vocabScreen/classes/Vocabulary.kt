package com.example.japhorizons.screens.vocabScreen.classes

data class Vocabulary(
    val kana: String,
    val kanji: String? = null,
    val romaji: String,
    val english: String,
    val endOfSection: Boolean = false
)
