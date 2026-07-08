package com.example.domain

import kotlin.math.ln

data class StrainInput(
    val timeInZone1Mins: Float, // 50-60% Max HR
    val timeInZone2Mins: Float, // 60-70%
    val timeInZone3Mins: Float, // 70-80%
    val timeInZone4Mins: Float, // 80-90%
    val timeInZone5Mins: Float  // 90-100%
)

data class StrainResult(
    val score: Float
)

object StrainCalculator {
    fun calculateScore(input: StrainInput): StrainResult {
        // Simple logarithmic formula matching Whoop's 0-21 scale concept.
        // Weight higher intensity zones more heavily.
        val weightedMins = (input.timeInZone1Mins * 0.1f) +
                (input.timeInZone2Mins * 0.5f) +
                (input.timeInZone3Mins * 1.5f) +
                (input.timeInZone4Mins * 3.0f) +
                (input.timeInZone5Mins * 5.0f)

        if (weightedMins <= 0f) return StrainResult(0f)

        // Using natural log to get a curve that asymptotes towards 21
        // ln(x + 1) * multiplier to map to 0-21
        val rawScore = 4f * ln(weightedMins + 1f)
        val finalScore = rawScore.coerceIn(0f, 21f)
        
        return StrainResult(
            score = Math.round(finalScore * 10f) / 10f // rounded to 1 decimal
        )
    }
}
