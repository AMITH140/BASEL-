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
import com.example.domain.StressProxyAlgorithm
import com.example.domain.SleepDebtTracker

@Composable
fun HomeScreen() {
    val recommendation = RecommendationEngine.getRecommendation(78, 14.2f, 0)
    val stress = StressProxyAlgorithm.estimate(52f, 50f, 45f, 50f)
    val sleepDebtMins = SleepDebtTracker.calculateDebt(410, 480)
    val sleepDebtStr = SleepDebtTracker.formatDebt(sleepDebtMins)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "OCT 24, 2023",
            color = BaselTextSecondary,
            fontSize = 12.sp,
            letterSpacing = 2.sp,
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
                StressProxyAlgorithm.StressLevel.LOW -> BaselGreen
                StressProxyAlgorithm.StressLevel.MODERATE -> BaselYellow
                StressProxyAlgorithm.StressLevel.HIGH -> BaselRed
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
            value = 78,
            color = BaselGreen,
            isEstimated = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SmallStatCard(
                modifier = Modifier.weight(1f),
                title = "SLEEP PERF.",
                value = "92%",
                lineColor = BaselGreen,
                subText = "Debt: $sleepDebtStr"
            )
            SmallStatCard(
                modifier = Modifier.weight(1f),
                title = "RESTING HR",
                value = "48 bpm",
                lineColor = BaselPrimary,
                subText = "-4 bpm vs baseline"
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        DayStrainCard()
        
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
            .border(1.dp, BaselDivider.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                color = BaselTextSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                fontFamily = BaselFontFamily
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = value,
                color = BaselTextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BaselFontFamily
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
            // Placeholder for sparkline
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
fun DayStrainCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(BaselSurface)
            .border(1.dp, BaselDivider.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
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
                    text = "4.2 of 12.0 target",
                    color = BaselGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = BaselFontFamily
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "14.2",
                color = BaselTextPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BaselFontFamily
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(BaselBackground)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.68f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            androidx.compose.ui.graphics.Brush.horizontalGradient(
                                colors = listOf(BaselPrimary, BaselGreen)
                            )
                        )
                )
            }
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
            .clip(RoundedCornerShape(24.dp))
            .background(BaselSurface)
            .border(1.dp, BaselDivider.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
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

