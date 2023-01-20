package com.example.japhorizons.screens.vocabScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LanguageToggle(
    name: String,
    selected: Int,
    modifier: Modifier = Modifier,
    updateLanguage: (Int) -> Unit
) {
    var isCollapsed by remember {
        mutableStateOf(true)
    }
    var selectedIndex by remember {
        mutableStateOf(selected)
    }
    val focusManager = LocalFocusManager.current

    Row(modifier = modifier.fillMaxWidth()) {
        Column (Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .clickable {
                        isCollapsed = !isCollapsed
                        focusManager.clearFocus()
                    }
                    .fillMaxWidth()
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(5.dp)
                )
                Icon(
                    Icons.Default.run {
                        if (isCollapsed)
                            KeyboardArrowDown
                        else
                            KeyboardArrowUp
                    },
                    contentDescription = "",
                    tint = Color.Gray,
                    modifier = Modifier.padding(5.dp)
                )
            }
            if (!isCollapsed) {
                Column {
                    LanguageToggleRow(
                        text = "かな",
                        index = 0,
                        selectedIndex = selectedIndex
                    ) {
                        updateLanguage(it)
                        selectedIndex = it
                    }
                    LanguageToggleRow(
                        text = "漢字",
                        index = 1,
                        selectedIndex = selectedIndex
                    ) {
                        updateLanguage(it)
                        selectedIndex = it
                    }
                    LanguageToggleRow(
                        text = "Romaji",
                        index = 2,
                        selectedIndex = selectedIndex
                    ) {
                        updateLanguage(it)
                        selectedIndex = it
                    }
                    LanguageToggleRow(
                        text = "English",
                        index = 3,
                        selectedIndex = selectedIndex
                    ) {
                        updateLanguage(it)
                        selectedIndex = it
                    }
                }
            }
        }
    }
}

@Composable
fun LanguageToggleRow(
    text: String,
    index: Int,
    selectedIndex: Int,
    updateSelected: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                updateSelected(index)
                focusManager.clearFocus()
            }
    ) {
        Text(
            text = text,
            fontWeight = if (selectedIndex == index) FontWeight.Bold
            else FontWeight.Normal,
            color = Color.White,
            modifier = Modifier.padding(start = 5.dp)
        )
        Checkbox(
            checked = selectedIndex == index,
            onCheckedChange = { updateSelected(index) },
            colors = magentaAndBlackCheckboxColors()
        )
    }
}

@Composable
fun magentaAndBlackCheckboxColors(): CheckboxColors {
    return CheckboxDefaults.colors(
        checkedColor = Color.Magenta,
        uncheckedColor = Color.White,
        checkmarkColor = Color.Black
    )
}
