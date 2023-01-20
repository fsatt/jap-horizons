package com.example.japhorizons.screens.vocabScreen.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.beust.klaxon.Klaxon
import com.example.japhorizons.R
import com.example.japhorizons.screens.vocabScreen.classes.Chapters
import java.io.InputStream

@Composable
fun readVocabFromJson() : Chapters {
    var dataText = "placeholder"
    println("Reading vocab from JSON file")
    runCatching {
        val inputStream: InputStream = LocalContext.current
            .resources
            .openRawResource(R.raw.mina_no_nihongo_vocabulary)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        String(buffer)
    }.onSuccess {
        dataText = it
    }.onFailure {
        dataText = "error"
    }
    return Klaxon().parse<Chapters>(dataText.trimIndent())!!
}
