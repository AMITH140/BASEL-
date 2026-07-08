package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DailySummary::class], version = 1, exportSchema = false)
abstract class BaselDatabase : RoomDatabase() {
    abstract fun summaryDao(): SummaryDao

    companion object {
        @Volatile
        private var INSTANCE: BaselDatabase? = null

        fun getDatabase(context: Context): BaselDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaselDatabase::class.java,
                    "basel_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
