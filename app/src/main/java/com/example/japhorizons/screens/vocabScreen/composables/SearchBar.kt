package com.example.japhorizons.screens.vocabScreen.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    value: String = "",
    updateSearchQuery: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(modifier = Modifier) {

    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(55.dp),
        value = value,
        onValueChange = updateSearchQuery,
        //label = { Text(text = "Search") },
        placeholder = {
            Text(
                text = "Search",
                fontStyle = FontStyle.Italic
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onAny = { focusManager.clearFocus() }
        ),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            backgroundColor = Color.DarkGray,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.DarkGray,
            placeholderColor = Color.Black,
            leadingIconColor = Color.Black
        ),
        shape = RoundedCornerShape(100f)
    )
}