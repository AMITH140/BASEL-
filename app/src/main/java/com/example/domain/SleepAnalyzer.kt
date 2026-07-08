package com.example.domain

import java.time.Instant
import java.time.LocalTime
import kotlin.math.abs

enum class SleepStage { AWAKE, REM, LIGHT, DEEP, UNKNOWN }

data class SleepStageData(
    val stage: SleepStage,
    val startTime: Instant,
    val endTime: Instant
)

data class SleepSessionInput(
    val actualSleepMinutes: Int,
    val sleepNeedMinutes: Int,
    val bedTimes: List<LocalTime>,
    val wakeTimes: List<LocalTime>,
    val stages: List<SleepStageData> = emptyList()
)

data class SleepAnalyzerResult(
    val performancePercentage: Float,
    val consistencyPercentage: Float,
    val stages: List<SleepStageData>
)

object SleepAnalyzer {
    fun analyze(input: SleepSessionInput): SleepAnalyzerResult {
        val consistency = calculateConsistency(input.bedTimes, input.wakeTimes)
        val performance = calculatePerformance(input.actualSleepMinutes, input.sleepNeedMinutes)
        
        return SleepAnalyzerResult(
            performancePercentage = Math.round(performance * 10f) / 10f,
            consistencyPercentage = Math.round(consistency * 10f) / 10f,
            stages = input.stages.sortedBy { it.startTime }
        )
    }

    private fun calculateConsistency(bedTimes: List<LocalTime>, wakeTimes: List<LocalTime>): Float {
        if (bedTimes.isEmpty() || wakeTimes.isEmpty()) return 100f

        val avgBedTimeMins = bedTimes.map { (it.hour + 12) % 24 * 60 + it.minute }.average()
        val avgWakeTimeMins = wakeTimes.map { it.hour * 60 + it.minute }.average()

        val bedTimeVariance = bedTimes.map { 
            val mins = (it.hour + 12) % 24 * 60 + it.minute
            abs(mins - avgBedTimeMins)
        }.average()

        val wakeTimeVariance = wakeTimes.map { 
            val mins = it.hour * 60 + it.minute
            abs(mins - avgWakeTimeMins)
        }.average()

        val totalVarianceMins = bedTimeVariance + wakeTimeVariance

        val consistency = 100f - (totalVarianceMins / 120f * 100f).toFloat()
        return consistency.coerceIn(0f, 100f)
    }

    private fun calculatePerformance(actual: Int, needed: Int): Float {
        if (needed <= 0) return 100f
        return (actual.toFloat() / needed.toFloat() * 100f).coerceIn(0f, 100f)
    }
}
