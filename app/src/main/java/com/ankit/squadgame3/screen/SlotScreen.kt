package com.ankit.squadgame3.screen

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.ankit.squadgame3.*
import com.ankit.squadgame3.R
import com.ankit.squadgame3.component.GradientButton
import com.ankit.squadgame3.component.SlotButton
import com.ankit.squadgame3.model.*
import com.ankit.squadgame3.ui.theme.BackgroundColor
import com.ankit.squadgame3.ui.theme.DarkColor

@Composable
fun SlotScreen(
    memberIndex: Int,
    squadIndex: Int,
    score: Int,
    enabled: Boolean,
    answerState: AnswerState,
    needUserName: Boolean,
    userName: String,
    onContinue: () -> Unit,
    onChangeUserName: (String) -> Unit,
    lottieId: Int,
    showLottie: Boolean,
    attemptsLeft: Int,
    onRotate: () -> Unit,
    resetGame: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if(needUserName) {
            EnterName(
                text = userName,
                onUpdateText = onChangeUserName,
                onContinue = onContinue
            )
        } else {
            SlotPlayScreen(
                lottieId,
                score,
                attemptsLeft,
                memberIndex,
                squadIndex,
                onRotate,
                enabled,
                resetGame,
                showLottie
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SlotPlayScreen(
    lottieId: Int,
    score: Int,
    attemptsLeft: Int,
    memberIndex: Int,
    squadIndex: Int,
    onRotate: () -> Unit,
    enabled: Boolean,
    resetGame: () -> Unit,
    showLottie: Boolean
) {
    var isPlaying by remember {
        mutableStateOf(true)
    }
    var speed by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(lottieId)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 131,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = true
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ScoreCard(
                score = score,
                attemptsLeft = attemptsLeft
            )
            SquadGameHeader(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .align(Alignment.CenterHorizontally)
            )
            AnimatedVisibility(attemptsLeft > 0) {
                SlotScreenBody(
                    memberIndex = memberIndex,
                    squadIndex = squadIndex,
                    onRotate = onRotate,
                    enabled = enabled
                )
            }
            AnimatedVisibility(attemptsLeft <= 0) {
                GameOver(
                    resetGame = resetGame,
                    score = score
                )
            }
        }
        if (showLottie) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun GameOver(resetGame: () -> Unit, score: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Game Over", style = MaterialTheme.typography.h5.copy(color = Color.White))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your Score: $score",
            style = MaterialTheme.typography.h5.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.height(32.dp))
        GradientButton(
            text = "Play Again",
            gradient = greenToBlueHorizontalGradient,
            onClick = resetGame,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        )
        Spacer(modifier = Modifier.height(86.dp))
    }
}

@Composable
fun ScoreCard(
    score: Int,
    attemptsLeft: Int
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp),
        contentColor = Color.White
    ) {
        Box(
            modifier = Modifier.background(DarkColor)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Score: $score")
                Text(text = "Attempts Left: $attemptsLeft")
            }
        }
    }

}

@Composable
fun SlotScreenBody(
    memberIndex: Int,
    squadIndex: Int,
    enabled: Boolean,
    onRotate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SlotItemComposable(memberIndex, 0)
            SlotItemComposable(squadIndex, 1)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Box(modifier = Modifier.padding(16.dp)) {
            SlotButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { onRotate() },
                text = "Spin",
                enabled = enabled
            )
        }
        Spacer(modifier = Modifier.height(86.dp))
    }
}

//@OptIn(ExperimentalAnimationApi::class)
//@Composable
//fun AnimatingSlotItem(currentItemIndex: Int) {
//
//}


@Composable
fun SquadGameHeader(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Squad Machine",
        style = MaterialTheme.typography.h3.copy(fontFamily = PermanentMarker, color = Color.White)
    )
}

@Composable
fun SlotItemComposable(
    slotItemIndex: Int,
    type: Int
) {
    val slotItem = if (type == 0)
        members[slotItemIndex]
    else
        squads[slotItemIndex]

    Box() {
        Card(
            modifier = Modifier
                .padding(16.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(6.dp),
            backgroundColor = Color.White
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = painterResource(id = slotItem.picture),
                contentDescription = "Squad Logo",
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(4.dp),
            color = Color(0x55000000)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                text = slotItem.name,
                style = MaterialTheme.typography.h6.copy(color = Color.White)
            )
        }

    }

}

@Composable
fun EnterName(text: String, onUpdateText: (String) -> Unit, onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter Name", style = MaterialTheme.typography.body1.copy(color = Color.White))
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = text,
            onValueChange = onUpdateText,
            textStyle = MaterialTheme.typography.body2.copy(color = Color.White)
        )
        SlotButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = onContinue,
            text = "Continue",
            enabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EnterNamePreview() {
    EnterName(
        "hello",
        {},
        {}
    )
}

//
//@Preview(showBackground = true)
//@Composable
//fun SlotScreenPreview() {
//    SlotScreenBody(
//        squadIndex = 0,
//        memberIndex = 0,
//        onRotate = {}
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun LogoPreview() {
//    SlotItemComposable(
//        0,
//        1
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun ScoreCardPreview() {
//    ScoreCard(1, 2)
//}

@Preview(showBackground = true)
@Composable
fun SlotScreenPreview() {
    SlotScreen(
        squadIndex = 0,
        memberIndex = 0,
        onRotate = {},
        showLottie = false,
        answerState = AnswerState.NOT_ANSWERED,
        score = 0,
        resetGame = {},
        attemptsLeft = 1,
        enabled = true,
        lottieId = R.raw.correct2,
        userName = "",
        onChangeUserName = {},
        onContinue = {},
        needUserName = false
    )
}
