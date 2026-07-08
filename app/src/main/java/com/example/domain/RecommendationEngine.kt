package com.example.domain

object RecommendationEngine {
    enum class RecommendationState(val label: String, val message: String) {
        PUSH("PUSH", "Your body is primed. Good day for high strain."),
        MAINTAIN("MAINTAIN", "Moderate recovery. Train, but don't chase a new max."),
        REST("REST", "Low recovery. Prioritize rest, hydration, and light movement only.")
    }

    fun getRecommendation(recoveryScore: Int, yesterdayStrain: Float, sleepDebtMinutes: Int): RecommendationState {
        var effectiveRecovery = recoveryScore
        if (yesterdayStrain > 16f) effectiveRecovery -= 10
        if (sleepDebtMinutes > 120) effectiveRecovery -= 10

        return when {
            effectiveRecovery >= 67 -> RecommendationState.PUSH
            effectiveRecovery >= 34 -> RecommendationState.MAINTAIN
            else -> RecommendationState.REST
        }
    }
}
