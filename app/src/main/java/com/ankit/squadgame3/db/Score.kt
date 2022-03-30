package com.ankit.squadgame3.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "score_table")
data class Score(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val score: Int
)