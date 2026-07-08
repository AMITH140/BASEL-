package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_summaries")
data class DailySummary(
    @PrimaryKey
    val dateString: String, // e.g. "2023-10-24"
    val recoveryScore: Int,
    val sleepPerformance: Int,
    val restingHr: Int,
    val dayStrain: Float,
    val strainTargetMin: Float,
    val strainTargetMax: Float,
    val recommendation: String
)
