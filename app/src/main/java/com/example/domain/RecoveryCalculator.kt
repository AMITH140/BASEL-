package com.example.domain

data class RecoveryInput(
    val hrvCurrent: Float?,
    val hrvBaseline: Float,
    val rhrCurrent: Float,
    val rhrBaseline: Float,
    val sleepPerformance: Float
)

data class RecoveryResult(
    val score: Int,
    val isEstimated: Boolean // true if HRV is unavailable
)

object RecoveryCalculator {
    fun calculateScore(input: RecoveryInput): RecoveryResult {
        val hrvMissing = input.hrvCurrent == null
        
        // RHR score: lower is better. Normalizing so that current == baseline -> 50 score contribution.
        // Actually, just simple logic for the prototype:
        val rhrRatio = input.rhrBaseline / input.rhrCurrent
        val rhrScore = (rhrRatio * 100f).coerceIn(0f, 100f)
        
        val finalScore = if (hrvMissing) {
            // Re-weight toward RHR (40%) and Sleep (60%)
            val score = (rhrScore * 0.4f) + (input.sleepPerformance * 0.6f)
            score.toInt().coerceIn(0, 100)
        } else {
            // Use HRV (40%), RHR (30%), Sleep (30%)
            val hrvRatio = input.hrvCurrent!! / input.hrvBaseline
            val hrvScore = (hrvRatio * 100f).coerceIn(0f, 100f)
            
            val score = (hrvScore * 0.4f) + (rhrScore * 0.3f) + (input.sleepPerformance * 0.3f)
            score.toInt().coerceIn(0, 100)
        }
        
        return RecoveryResult(
            score = finalScore,
            isEstimated = hrvMissing
        )
    }
}
