package com.example.domain

object RecoveryAlgorithm {
    /**
     * Calculates a composite recovery score (0-100).
     * Inputs:
     * - hrv: overnight HRV trend vs. personal 30-day baseline (nullable)
     * - restingHrBase: personal baseline RHR
     * - restingHrCurrent: current night RHR
     * - sleepPerformance: duration vs personal need, quality, consistency
     * - priorDayStrain: cumulative strain from previous day
     * 
     * Degrades gracefully if HRV is absent.
     */
    fun calculateScore(hrv: Float?, restingHrBase: Float, restingHrCurrent: Float, sleepPerformance: Float, priorDayStrain: Float): Pair<Int, Boolean> {
        val hrvMissing = hrv == null
        
        // Normalizes metrics to 0-100 for score contribution
        val rhrScore = ((restingHrBase / restingHrCurrent) * 100f).coerceIn(0f, 100f)
        val strainPenalty = (priorDayStrain / 21f * 10f)
        
        val finalScore = if (hrvMissing) {
            // Fallback weighting: RHR 40%, Sleep 60%
            val score = (rhrScore * 0.4f) + (sleepPerformance * 0.6f) - strainPenalty
            score.toInt().coerceIn(0, 100)
        } else {
            // Standard weighting: HRV 40%, RHR 30%, Sleep 30%
            val score = (hrv!! * 0.4f) + (rhrScore * 0.3f) + (sleepPerformance * 0.3f) - strainPenalty
            score.toInt().coerceIn(0, 100)
        }
        
        return Pair(finalScore, hrvMissing)
    }
}
