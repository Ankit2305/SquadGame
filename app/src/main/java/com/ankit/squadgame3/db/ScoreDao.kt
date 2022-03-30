package com.ankit.squadgame3.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {

    @Insert
    fun insertScore(score: Score)

    @Query("SELECT * FROM score_table ORDER BY score DESC")
    fun getScoreList(): LiveData<List<Score>>
}