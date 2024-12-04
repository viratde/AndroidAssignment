package com.myjar.jarassignment.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myjar.jarassignment.R
import com.myjar.jarassignment.ui.theme.JarAssignmentTheme


@Composable
fun ItemSearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit = {}
) {

    BasicTextField(
        value = query,
        onValueChange = {
            onQueryChange(it)
        },
        decorationBox = {
            Box(
                modifier = modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 6.dp
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        1.dp,
                        Color.Black,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                if (query.isEmpty()) {
                    Text(
                        text = stringResource(R.string.enter_query),
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Color.Black
                    )
                }
                it()
            }

        },
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = Color.Black
        )
    )

}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun ItemSearchBarPreview() {
    JarAssignmentTheme {
        ItemSearchBar(
            query = "Apple Watch",
            onQueryChange = {}
        )
    }
}