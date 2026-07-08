package com.example.domain

object StressProxyEstimator {
    enum class StressLevel { LOW, MODERATE, HIGH }
    
    fun estimate(rhrCurrent: Float, rhrBase: Float, hrvCurrent: Float, hrvBase: Float): StressLevel {
        val rhrElevated = rhrCurrent > (rhrBase * 1.1f)
        val hrvReduced = hrvCurrent < (hrvBase * 0.9f)
        
        return if (rhrElevated && hrvReduced) {
            StressLevel.HIGH
        } else if (rhrElevated || hrvReduced) {
            StressLevel.MODERATE
        } else {
            StressLevel.LOW
        }
    }
}
