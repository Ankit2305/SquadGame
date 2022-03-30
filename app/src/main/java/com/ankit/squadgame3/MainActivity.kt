package com.ankit.squadgame3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ankit.squadgame3.db.ScoreDatabase
import com.ankit.squadgame3.screen.Home
import com.ankit.squadgame3.screen.LeadersBoardScreen
import com.ankit.squadgame3.screen.SlotScreen
import com.ankit.squadgame3.screen.SlotScreenBody
import com.ankit.squadgame3.ui.theme.SquadGame3Theme

class MainActivity : ComponentActivity() {

    val slotViewModel by viewModels<SlotViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slotViewModel.repository = ScoreRepository(ScoreDatabase.getInstance(this))
        setContent {
            SquadGame3Theme {
                Navigation(slotViewModel = slotViewModel) {
                    this.finishAffinity()
                }
            }
        }
    }
}

@Composable
fun Navigation(slotViewModel: SlotViewModel, exit: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home(
                navController = navController,
                resetGame = {
                    slotViewModel.resetGame()
                    slotViewModel.resetUserName()
                },
                exit = exit
            )
        }
        composable("slot_screen") {
            SquadGameActivityScreen(slotViewModel = slotViewModel)
        }
        composable("leaderboard") {
            LeadersBoardScreen(slotViewModel = slotViewModel)
        }
    }
}

@Composable
fun SquadGameActivityScreen(slotViewModel: SlotViewModel) {
    SlotScreen(
        memberIndex = slotViewModel.currentMemberIndex,
        squadIndex = slotViewModel.currentSquadIndex,
        score = slotViewModel.score,
        showLottie = slotViewModel.showLottie,
        enabled = slotViewModel.enabled,
        answerState = slotViewModel.answerState,
        attemptsLeft = slotViewModel.attempts,
        onRotate = slotViewModel::rotate,
        resetGame = slotViewModel::resetGame,
        lottieId = slotViewModel.lottieId,
        userName = slotViewModel.userName,
        onContinue = slotViewModel::onContinue,
        onChangeUserName = slotViewModel::updateUserName,
        needUserName = slotViewModel.needUserName
    )
}

@Preview(showBackground = true)
@Composable
fun SquadGameActivityPreview() {
    SquadGame3Theme {
        SquadGameActivityScreen(
            slotViewModel = SlotViewModel()
        )
    }
}