package com.example.data

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.RecoveryCalculator
import com.example.domain.RecoveryInput
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.flow.firstOrNull

class HealthConnectWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val client = HealthConnectClient.getOrCreate(applicationContext)
        val database = BaselDatabase.getDatabase(applicationContext)
        val dao = database.summaryDao()

        // Sync data for the past 24 hours
        val endTime = Instant.now()
        val startTime = endTime.minus(1, ChronoUnit.DAYS)
        val timeRange = TimeRangeFilter.between(startTime, endTime)

        try {
            // Read Sleep Sessions
            val sleepRequest = ReadRecordsRequest(
                recordType = SleepSessionRecord::class,
                timeRangeFilter = timeRange
            )
            val sleepResponse = client.readRecords(sleepRequest)
            var actualSleepMins = 0
            if (sleepResponse.records.isNotEmpty()) {
                val latestSleep = sleepResponse.records.maxByOrNull { it.endTime }
                if (latestSleep != null) {
                    actualSleepMins = ChronoUnit.MINUTES.between(latestSleep.startTime, latestSleep.endTime).toInt()
                }
            }

            // Read RHR
            val rhrRequest = ReadRecordsRequest(
                recordType = RestingHeartRateRecord::class,
                timeRangeFilter = timeRange
            )
            val rhrResponse = client.readRecords(rhrRequest)
            val rhr = rhrResponse.records.lastOrNull()?.beatsPerMinute?.toFloat() ?: 60f

            // Read HRV
            val hrvRequest = ReadRecordsRequest(
                recordType = HeartRateVariabilityRmssdRecord::class,
                timeRangeFilter = timeRange
            )
            val hrvResponse = client.readRecords(hrvRequest)
            val hrv = hrvResponse.records.lastOrNull()?.heartRateVariabilityMillis?.toFloat()

            // Calculate metrics
            val recoveryResult = RecoveryCalculator.calculateScore(
                RecoveryInput(
                    hrvCurrent = hrv,
                    hrvBaseline = 45f,
                    rhrCurrent = rhr,
                    rhrBaseline = 55f,
                    sleepPerformance = (actualSleepMins.toFloat() / 480f) * 100f
                )
            )

            val todayStr = ZonedDateTime.now(ZoneId.systemDefault()).toLocalDate().toString()
            
            val summary = DailySummary(
                dateString = todayStr,
                recoveryScore = recoveryResult.score,
                sleepPerformance = (actualSleepMins.toFloat() / 480f * 100f).toInt().coerceIn(0, 100),
                restingHr = rhr.toInt(),
                dayStrain = 12.5f, // Mock strain for now, would require parsing HR zones
                strainTargetMin = 10f,
                strainTargetMax = 15f,
                recommendation = if (recoveryResult.score > 66) "Push" else if (recoveryResult.score > 33) "Maintain" else "Rest"
            )

            dao.insertSummary(summary)
            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.retry()
        }
    }
}
