package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SummaryDao {
    @Query("SELECT * FROM daily_summaries WHERE dateString = :date LIMIT 1")
    fun getSummaryForDate(date: String): Flow<DailySummary?>

    @Query("SELECT * FROM daily_summaries ORDER BY dateString DESC LIMIT 30")
    fun getRecentSummaries(): Flow<List<DailySummary>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: DailySummary)
}
