package com.ankit.squadgame3

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.ankit.squadgame3.ui.theme.Blue
import com.ankit.squadgame3.ui.theme.Green
import com.ankit.squadgame3.ui.theme.Orange
import com.ankit.squadgame3.ui.theme.Red

enum class AnswerState {
    NOT_ANSWERED,
    CORRECT,
    INCORRECT
}

val greenToBlueHorizontalGradient = Brush.horizontalGradient(
    colors = listOf(
        Green,
        Blue
    )
)
val redToOrangeHorizontalGradient = Brush.horizontalGradient(
    colors = listOf(
        Red,
        Orange
    )
)

val PermanentMarker = FontFamily(
    Font(R.font.permanentmarker)
)