package com.ankit.squadgame3.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Score::class], version = 1)
abstract class ScoreDatabase: RoomDatabase() {


    abstract fun scoreDao(): ScoreDao

    companion object {
        var instance: ScoreDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ScoreDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    ScoreDatabase::class.java,
                    "score_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}