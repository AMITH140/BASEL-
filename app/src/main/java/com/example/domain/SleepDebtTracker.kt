package com.example.domain

object SleepDebtTracker {
    fun calculateDebt(actualSleepTotal: Int, neededSleepTotal: Int): Int {
        return neededSleepTotal - actualSleepTotal
    }
    
    fun formatDebt(debtMinutes: Int): String {
        val sign = if (debtMinutes > 0) "-" else "+"
        val absMins = kotlin.math.abs(debtMinutes)
        val h = absMins / 60
        val m = absMins % 60
        return "$sign${h}h ${m}m"
    }
}
