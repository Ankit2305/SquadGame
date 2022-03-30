package com.ankit.squadgame3.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ankit.squadgame3.component.SlotButton
import com.ankit.squadgame3.component.SlotRedButton
import com.ankit.squadgame3.ui.theme.DarkColor

@Composable
fun Home(navController: NavController?, resetGame: () -> Unit, exit: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SquadGameHeader()
        Spacer(modifier = Modifier.height(64.dp))
        SlotButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                navController?.navigate("slot_screen")
                resetGame()
            },
            text = "Play",
            enabled = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        SlotButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(40.dp),
        onClick = {
            navController?.navigate("leaderboard")
        },
        text = "Leaderboard",
        enabled = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        SlotRedButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                exit()
            },
            text = "Quit",
            enabled = true
        )
    }
}

@Preview
@Composable
fun PreviewHome() {
    Home(null, {}, {})
}