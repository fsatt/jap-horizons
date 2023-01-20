package com.example.japhorizons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.japhorizons.screens.vocabScreen.VocabScreen
import com.example.japhorizons.ui.theme.JapHorizonsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JapHorizonsTheme {
                VocabScreen()
            }
        }
    }
}