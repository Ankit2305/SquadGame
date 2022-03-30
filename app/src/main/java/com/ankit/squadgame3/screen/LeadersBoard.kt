package com.ankit.squadgame3.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ankit.squadgame3.SlotViewModel
import com.ankit.squadgame3.db.Score
import com.ankit.squadgame3.ui.theme.BackgroundColor


@Composable
fun LeadersBoardScreen(slotViewModel: SlotViewModel) {
    val scores by slotViewModel.getScores().observeAsState(emptyList())
    LeadersBoard(scores = scores)
}

@Composable
fun LeadersBoard(scores: List<Score>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Leaderboard",
            style = MaterialTheme.typography.h5.copy(Color.White)
        )
        LeadersBoardList(scores = scores)
    }
}

@Composable
fun LeadersBoardList(scores: List<Score>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(scores) { score ->
            SingleScore(score = score)
        }
    }
}

@Composable
fun SingleScore(score: Score) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0x55FFFFFF)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${score.name}", style = MaterialTheme.typography.body1.copy(Color.White))
            Text(text = "${score.score}", style = MaterialTheme.typography.body1.copy(Color.White))
        }
    }
}