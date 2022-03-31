package com.ankit.squadgame3

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
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
    var nextMemberIndex by mutableStateOf((members.indices).random())
    var nextSquadIndex by mutableStateOf((squads.indices).random())
    var answerState by mutableStateOf(AnswerState.NOT_ANSWERED)
    var showLottie by mutableStateOf(false)
    var userName by mutableStateOf("")

    var score by mutableStateOf(0)
    var attempts by mutableStateOf(5)
    var enabled by mutableStateOf(true)
    var needUserName by mutableStateOf(true)
    var lottieId by mutableStateOf(R.raw.correct2)

    var repository: ScoreRepository? = null

    var animatingSquadIndex by mutableStateOf(0)
    var offsetSquadY by mutableStateOf(0f)
    var offsetMemberY by mutableStateOf(0f)
    var slotHeight = 0f
    var delta = 1f

    fun rotate() {
        randomRotation()
    }

    fun updateUserName(name: String) {
        userName = name
    }

    fun onContinue() {
        needUserName = false
    }

    suspend fun spinSquadSlot(targetSquadIndex: Int, deltaIncrement: Boolean) {
        Log.i("DebugTag", "spinSlot: $currentSquadIndex")
        while (offsetSquadY > -slotHeight) {
            offsetSquadY -= delta
            if(deltaIncrement) {
                delta += 1
            } else {
                delta -= 1
            }
            delay(5L)
        }
        currentSquadIndex = targetSquadIndex
        offsetSquadY = 0f
    }

    suspend fun spinMemberSlot(targetMemberIndex: Int, deltaIncrement: Boolean) {
        Log.i("DebugTag", "spinSlot: $currentMemberIndex")
        while (offsetMemberY > -slotHeight) {
            offsetMemberY -= delta
            delay(5L)
        }
        currentMemberIndex = targetMemberIndex
        offsetMemberY = 0f
    }

    fun randomRotation() {
        viewModelScope.launch {
            enabled = false
            var increment = true
            repeat(50) {
                increment = it < 25
                nextMemberIndex = (members.indices).random()
                nextSquadIndex = if(it < 49) (squads.indices).random()
                        else pickSquad(members[nextMemberIndex].squad, squads[(squads.indices).random()])
                val job1 = launch { spinSquadSlot(nextSquadIndex, increment) }
                val job2 = launch { spinMemberSlot(nextMemberIndex, increment) }
                job1.join()
                job2.join()
            }
            delay(750)
            if (members[currentMemberIndex].squad == squads[currentSquadIndex]) {
                incrementScore()
                lottieId = R.raw.correct2
                answerState = AnswerState.CORRECT
            } else {
                lottieId = R.raw.incorrect
                answerState = AnswerState.INCORRECT
            }

            showLottie = true
            delay(2000)
            showLottie = false
            decrementAttempt()
            enabled = true
        }
    }

    fun pickSquad(correct: Squad, random: Squad): Int {
        val randomNumber = (1..10).random()
        val selectedSquad = if (randomNumber <= 4)
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
        if (attempts == 0) {
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

    fun hideLottie() {
        showLottie = false
    }
}