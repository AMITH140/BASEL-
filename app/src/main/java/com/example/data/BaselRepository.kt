package com.example.data

import kotlinx.coroutines.flow.Flow

class BaselRepository(private val summaryDao: SummaryDao) {
    fun getSummaryForDate(date: String): Flow<DailySummary?> = summaryDao.getSummaryForDate(date)
    fun getRecentSummaries(): Flow<List<DailySummary>> = summaryDao.getRecentSummaries()

    suspend fun insertSummary(summary: DailySummary) {
        summaryDao.insertSummary(summary)
    }
}
