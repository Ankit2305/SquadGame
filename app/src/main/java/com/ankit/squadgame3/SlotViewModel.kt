package com.ankit.squadgame3

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankit.squadgame3.db.Score
import com.ankit.squadgame3.db.ScoreDatabase
import com.ankit.squadgame3.model.*
import kotlinx.coroutines.*
import java.util.*

class SlotViewModel : ViewModel() {

    init {
        buildData()
    }

    var currentMemberIndex by mutableStateOf((members.indices).random())
    var currentSquadIndex by mutableStateOf((squads.indices).random())
    var answerState by mutableStateOf(AnswerState.NOT_ANSWERED)
    var showLottie by mutableStateOf(false)
    var userName by mutableStateOf("")

    var score by mutableStateOf(0)
    var attempts by mutableStateOf(5)
    var enabled by mutableStateOf(true)
    var needUserName by mutableStateOf(true)
    var lottieId by mutableStateOf(R.raw.correct2)

    var repository: ScoreRepository? = null

    fun rotate() {
        randomRotation()
    }

    fun updateUserName(name: String) {
        userName = name
    }

    fun onContinue() {
        needUserName = false
    }

    fun randomRotation() {
        viewModelScope.launch {
            enabled = false
            repeat(9) {
                currentMemberIndex = (members.indices).random()
                currentSquadIndex = (squads.indices).random()
                delay(100)
            }
            currentMemberIndex = (members.indices).random()
            currentSquadIndex = pickSquad(members[currentMemberIndex].squad, squads[(squads.indices).random()])
            delay(100)
            if (members[currentMemberIndex].squad == squads[currentSquadIndex]) {
                incrementScore()
                lottieId = R.raw.correct2
                answerState = AnswerState.CORRECT
            } else {
                lottieId = R.raw.incorrect
                answerState = AnswerState.INCORRECT
            }

            showLottie = true
            delay(1500)
            showLottie = false
            decrementAttempt()
            enabled = true
        }
    }

    fun pickSquad(correct: Squad, random: Squad): Int {
        val randomNumber = (1..10).random()
        val selectedSquad = if(randomNumber <= 4)
            correct
        else
            random
        return squads.indexOf(selectedSquad)
    }

    fun incrementScore() {
        score++
    }

    fun decrementAttempt() {
        attempts--
        if(attempts == 0) {
            saveScore()
        }
    }

    fun resetGame() {
        score = 0
        attempts = 5
        answerState = AnswerState.NOT_ANSWERED
    }

    fun resetUserName() {
        userName = ""
        needUserName = true
    }

    fun saveScore() {
        viewModelScope.launch(Dispatchers.IO) {
            repository?.saveScore(Score(UUID.randomUUID(), userName, score))
        }
    }

    fun getScores(): LiveData<List<Score>> {
        return repository?.getScores()!!
    }
}