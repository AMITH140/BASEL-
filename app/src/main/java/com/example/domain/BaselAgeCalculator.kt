package com.example.domain

object BaselAgeCalculator {
    data class BaselAgeResult(val age: Int, val delta: Int, val drivingFactor: String)

    fun estimate(chronologicalAge: Int, rhr: Float, hrv: Float, sleepConsistency: Float, avgRecovery: Float): BaselAgeResult {
        var delta = 0
        var drivingFactor = ""
        
        if (hrv > 60) {
            delta -= 1
            drivingFactor = "Your HRV trend is improving your biological age — excellent cardiovascular health."
        } else if (hrv < 30) {
            delta += 2
            drivingFactor = "Your HRV trend is adding 2 years — consider more recovery."
        }
        
        if (sleepConsistency > 90f) {
            delta -= 1
        } else if (sleepConsistency < 70f) {
            delta += 1
            if (drivingFactor.isEmpty()) {
                drivingFactor = "Improving sleep consistency could help lower your biological age."
            }
        }
        
        if (avgRecovery > 70f) {
            delta -= 1
        }
        
        return BaselAgeResult(chronologicalAge + delta, delta, drivingFactor.ifEmpty { "Balanced metrics. Keep up the good work." })
    }
}
