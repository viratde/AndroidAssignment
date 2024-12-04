package com.myjar.jarassignment.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ItemCardDetailsText(
    text: String?,
    type: String,
    modifier: Modifier = Modifier
) {
    if (text != null) {
        Text(
            text = "$type: $text",
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            modifier = modifier
        )
    }
}