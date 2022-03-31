package com.ankit.squadgame3.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ankit.squadgame3.R
import com.ankit.squadgame3.model.members
import com.ankit.squadgame3.model.squads
import com.ankit.squadgame3.screen.SlotItemComposable
import com.ankit.squadgame3.ui.theme.DarkColor
import kotlinx.coroutines.launch

@Composable
fun AnimatingSlot(slotIndex: Int, type: Int, offsetY: Int) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val height = with(LocalDensity.current) {
        150.dp.toPx()
    }

    SlotItemComposable(
        modifier = Modifier
            .offset {
                IntOffset(
                    0,
                    offsetY
                )
            },
        slotItemIndex = slotIndex,
        type = type
    )
}

@Composable
fun AnimatingSlots(currentIndex: Int, targetIndex: Int, type: Int, offsetY: Int) {
    val slotSize = if (type == 0) members.size else squads.size
    Box(modifier = Modifier
        .height(490.dp)
        .width(170.dp)
        .background(DarkColor)
    ) {
        Column(modifier = Modifier
            .height(300.dp)
            .width(150.dp)
            .padding(bottom = 20.dp)
            .align(Alignment.BottomCenter)) {
            AnimatingSlot(slotIndex = currentIndex, type = type, offsetY = offsetY)
            AnimatingSlot(slotIndex = targetIndex, type = type, offsetY = offsetY)
        }
        Box(modifier = Modifier
            .height(170.dp)
            .width(170.dp)
            .align(Alignment.TopCenter)
            .background(DarkColor))
        Box(modifier = Modifier
            .height(150.dp)
            .width(170.dp)
            .align(Alignment.BottomCenter)
            .background(DarkColor))
    }
}

@Preview
@Composable
fun PreviewAnimatingSlots() {
    AnimatingSlots(currentIndex = 0, targetIndex = 1, type = 1, offsetY = 0)
}