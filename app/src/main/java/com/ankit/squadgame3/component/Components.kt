package com.ankit.squadgame3.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ankit.squadgame3.redToOrangeHorizontalGradient
import com.ankit.squadgame3.ui.theme.Blue
import com.ankit.squadgame3.ui.theme.Green

@Composable
fun SlotButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean
) {
    GradientButton(
        modifier = modifier,
        text  = text,
        gradient = Brush.horizontalGradient(
            colors = listOf(
                Green,
                Blue
            )
        ),
        onClick = onClick,
        enabled = enabled
    )
}

@Composable
fun SlotRedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    enabled: Boolean
) {
    GradientButton(
        modifier = modifier,
        text  = text,
        gradient = redToOrangeHorizontalGradient,
        onClick = onClick,
        enabled = enabled
    )
}

@Preview
@Composable
fun SlotButtonPreview() {

}

@Composable
fun GradientButton(
    text: String,
    gradient : Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent, contentColor = Color.White),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text
            )
        }
    }
}