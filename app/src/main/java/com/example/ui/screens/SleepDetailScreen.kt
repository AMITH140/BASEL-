package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.SleepStage
import com.example.domain.SleepStageData
import com.example.ui.components.Hypnogram
import com.example.ui.theme.*
import java.time.Instant
import java.time.temporal.ChronoUnit

@Composable
fun SleepDetailScreen(viewModel: com.example.BaselViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val summary by viewModel.todaySummary.collectAsState()
    
    // Mock stage data for demonstration since Health Connect parsing is complex
    val now = Instant.now()
    val startSleep = now.minus(8, ChronoUnit.HOURS)
    val stages = listOf(
        SleepStageData(SleepStage.LIGHT, startSleep, startSleep.plus(60, ChronoUnit.MINUTES)),
        SleepStageData(SleepStage.DEEP, startSleep.plus(60, ChronoUnit.MINUTES), startSleep.plus(120, ChronoUnit.MINUTES)),
        SleepStageData(SleepStage.REM, startSleep.plus(120, ChronoUnit.MINUTES), startSleep.plus(180, ChronoUnit.MINUTES)),
        SleepStageData(SleepStage.LIGHT, startSleep.plus(180, ChronoUnit.MINUTES), startSleep.plus(240, ChronoUnit.MINUTES)),
        SleepStageData(SleepStage.AWAKE, startSleep.plus(240, ChronoUnit.MINUTES), startSleep.plus(250, ChronoUnit.MINUTES)),
        SleepStageData(SleepStage.DEEP, startSleep.plus(250, ChronoUnit.MINUTES), startSleep.plus(320, ChronoUnit.MINUTES)),
        SleepStageData(SleepStage.REM, startSleep.plus(320, ChronoUnit.MINUTES), startSleep.plus(400, ChronoUnit.MINUTES)),
        SleepStageData(SleepStage.LIGHT, startSleep.plus(400, ChronoUnit.MINUTES), startSleep.plus(480, ChronoUnit.MINUTES))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("SLEEP DETAIL", color = BaselTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
        Spacer(modifier = Modifier.height(24.dp))
        
        Hypnogram(stages = stages)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            LegendItem("Awake", SleepAwake)
            LegendItem("REM", SleepREM)
            LegendItem("Light", SleepLight)
            LegendItem("Deep", SleepDeep)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(BaselSurface)
                .border(1.dp, BaselDivider, RoundedCornerShape(8.dp))
                .padding(24.dp)
        ) {
            Column {
                Text("PERFORMANCE", color = BaselTextSecondary, fontSize = 12.sp, fontFamily = BaselFontFamily, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("${summary?.sleepPerformance ?: 0}%", color = BaselTextPrimary, fontSize = 48.sp, fontFamily = BaselNumberFontFamily, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LegendItem(label: String, color: androidx.compose.ui.graphics.Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(12.dp).clip(RoundedCornerShape(2.dp)).background(color))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, color = BaselTextSecondary, fontSize = 12.sp, fontFamily = BaselFontFamily)
    }
}
