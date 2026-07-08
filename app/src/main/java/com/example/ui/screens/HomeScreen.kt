package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.RingChart
import com.example.ui.theme.*
import com.example.domain.RecommendationEngine
import com.example.domain.StressProxyEstimator
import com.example.domain.SleepDebtTracker

@Composable
fun HomeScreen(
    viewModel: com.example.BaselViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateToSleep: () -> Unit = {},
    onNavigateToStrain: () -> Unit = {}
) {
    val summary by viewModel.todaySummary.collectAsState()
    
    val recoveryScore = summary?.recoveryScore ?: 0
    val strainScore = summary?.dayStrain ?: 0f
    val sleepScore = summary?.sleepPerformance ?: 0
    val restingHr = summary?.restingHr ?: 0
    
    val recommendation = RecommendationEngine.getRecommendation(recoveryScore, strainScore, 0)
    val stress = StressProxyEstimator.estimate(restingHr.toFloat(), 50f, 45f, 50f)
    val sleepDebtMins = SleepDebtTracker.calculateDebt((sleepScore / 100f * 480).toInt(), 480)
    val sleepDebtStr = SleepDebtTracker.formatDebt(sleepDebtMins)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "SYSTEM CONFIGURATION",
            color = BaselTextSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            fontFamily = BaselFontFamily
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Good morning, Alex.",
                color = BaselTextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BaselFontFamily
            )
            val stressColor = when (stress) {
                StressProxyEstimator.StressLevel.LOW -> BaselGreen
                StressProxyEstimator.StressLevel.MODERATE -> BaselYellow
                StressProxyEstimator.StressLevel.HIGH -> BaselRed
            }
            Box(modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(stressColor.copy(alpha = 0.2f))
                .padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("STRESS: ${stress.name}", color = stressColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        RingChart(
            value = recoveryScore,
            color = if (recoveryScore > 66) BaselGreen else if (recoveryScore > 33) BaselYellow else BaselRed,
            isEstimated = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .aspectRatio(1f)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SmallStatCard(
                modifier = Modifier.weight(1f).clickable { onNavigateToSleep() },
                title = "SLEEP PERF.",
                value = "$sleepScore%",
                lineColor = BaselGreen,
                subText = "Debt: $sleepDebtStr"
            )
            SmallStatCard(
                modifier = Modifier.weight(1f),
                title = "RESTING HR",
                value = "$restingHr bpm",
                lineColor = BaselPrimary,
                subText = "Based on overnight reading"
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        DayStrainCard(strainScore, onClick = onNavigateToStrain)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        RecommendationCard(recommendation)

        Spacer(modifier = Modifier.height(24.dp))

        com.example.ui.components.BaselButton(text = "Log Workout", onClick = { /* log workout */ })
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SmallStatCard(modifier: Modifier = Modifier, title: String, value: String, lineColor: Color, subText: String = "") {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(24.dp))
            .background(BaselSurface)
            .border(1.dp, BaselDivider.copy(alpha=0.5f), RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = title,
                color = BaselTextSecondary,
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BaselFontFamily
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = value,
                color = BaselTextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BaselNumberFontFamily
            )
            if (subText.isNotEmpty()) {
                Text(
                    text = subText,
                    color = BaselTextSecondary,
                    fontSize = 10.sp,
                    fontFamily = BaselFontFamily
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(lineColor)
            )
        }
    }
}

@Composable
fun DayStrainCard(strainScore: Float = 0f, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(BaselSurface)
            .border(1.dp, BaselDivider.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DAY STRAIN",
                    color = BaselTextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    fontFamily = BaselFontFamily
                )
                Text(
                    text = "$strainScore of 21.0 max",
                    color = BaselPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = BaselNumberFontFamily
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(BaselSurfaceVariant)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(strainScore / 21f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(BaselPrimary)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Building strain. Consider a light workout to reach your target.", color = BaselTextSecondary, fontSize = 12.sp, fontFamily = BaselFontFamily)
        }
    }
}

@Composable
fun RecommendationCard(recommendation: RecommendationEngine.RecommendationState) {
    val stateColor = when (recommendation) {
        RecommendationEngine.RecommendationState.PUSH -> BaselGreen
        RecommendationEngine.RecommendationState.MAINTAIN -> BaselYellow
        RecommendationEngine.RecommendationState.REST -> BaselRed
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BaselSurfaceVariant)
            .border(1.dp, BaselDivider.copy(alpha=0.5f), RoundedCornerShape(16.dp))
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(80.dp)
                    .background(stateColor)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "TODAY: ${recommendation.label}",
                    color = stateColor,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = BaselFontFamily
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = recommendation.message,
                    color = BaselTextPrimary,
                    fontSize = 14.sp,
                    fontFamily = BaselFontFamily
                )
            }
        }
    }
}
