package com.ankit.squadgame3

import android.content.Context
import androidx.lifecycle.LiveData
import com.ankit.squadgame3.db.Score
import com.ankit.squadgame3.db.ScoreDatabase

class ScoreRepository(val database: ScoreDatabase?) {

    fun saveScore(score: Score) {
        database?.scoreDao()?.insertScore(score)
    }

    fun getScores(): LiveData<List<Score>>? {
        return database?.scoreDao()?.getScoreList()
    }

}