package com.example.domain

import java.time.LocalTime
import kotlin.math.abs

object SleepAlgorithm {
    /**
     * Calculates sleep consistency based on variance in bedtime and wake times over a rolling period.
     * Returns a score between 0.0 and 1.0 (or 0-100).
     */
    fun calculateConsistency(bedTimes: List<LocalTime>, wakeTimes: List<LocalTime>): Float {
        if (bedTimes.isEmpty() || wakeTimes.isEmpty()) return 100f
        
        // Calculate average bed time (converting to minutes from noon to handle midnight crossing)
        val avgBedTimeMins = bedTimes.map { (it.hour + 12) % 24 * 60 + it.minute }.average()
        val avgWakeTimeMins = wakeTimes.map { it.hour * 60 + it.minute }.average()
        
        // Calculate variance (average deviation in minutes)
        val bedTimeVariance = bedTimes.map { 
            val mins = (it.hour + 12) % 24 * 60 + it.minute
            abs(mins - avgBedTimeMins)
        }.average()
        
        val wakeTimeVariance = wakeTimes.map { 
            val mins = it.hour * 60 + it.minute
            abs(mins - avgWakeTimeMins)
        }.average()
        
        val totalVarianceMins = bedTimeVariance + wakeTimeVariance
        
        // Assume 0 variance = 100%, 120+ mins variance = 0%
        val consistency = 100f - (totalVarianceMins / 120f * 100f).toFloat()
        return consistency.coerceIn(0f, 100f)
    }

    /**
     * Calculates sleep performance vs personal sleep need.
     */
    fun calculatePerformance(actualSleepMinutes: Int, sleepNeedMinutes: Int, consistencyScore: Float): Float {
        val durationScore = (actualSleepMinutes.toFloat() / sleepNeedMinutes.toFloat() * 100f).coerceIn(0f, 100f)
        // Duration = 70% weight, Consistency = 30% weight
        val performance = (durationScore * 0.7f) + (consistencyScore * 0.3f)
        return performance.coerceIn(0f, 100f)
    }
}
