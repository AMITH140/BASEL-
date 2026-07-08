package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.components.LineChart
import kotlin.random.Random

enum class TrendMetric(val label: String, val color: Color) {
    RECOVERY("RECOVERY", BaselGreen),
    STRAIN("STRAIN", BaselPrimary),
    SLEEP("SLEEP", BaselPrimaryDark)
}

enum class TrendRange(val label: String, val days: Int) {
    D7("7D", 7),
    D30("30D", 30),
    D90("90D", 90)
}

@Composable
fun TrendsScreen() {
    var selectedMetric by remember { mutableStateOf(TrendMetric.RECOVERY) }
    var selectedRange by remember { mutableStateOf(TrendRange.D30) }

    // Generate mock data based on selections
    val mockData = remember(selectedMetric, selectedRange) {
        val random = Random(selectedMetric.ordinal + selectedRange.ordinal)
        val count = selectedRange.days
        List(count) { 
            when (selectedMetric) {
                TrendMetric.RECOVERY -> 40f + random.nextFloat() * 50f
                TrendMetric.STRAIN -> 8f + random.nextFloat() * 10f
                TrendMetric.SLEEP -> 60f + random.nextFloat() * 40f
            }
        }
    }

    val avgValue = mockData.average().toFloat()
    val bestValue = mockData.maxOrNull() ?: 0f
    val worstValue = mockData.minOrNull() ?: 0f

    val minValue = when (selectedMetric) {
        TrendMetric.RECOVERY -> 0f
        TrendMetric.STRAIN -> 0f
        TrendMetric.SLEEP -> 0f
    }
    
    val maxValue = when (selectedMetric) {
        TrendMetric.RECOVERY -> 100f
        TrendMetric.STRAIN -> 21f
        TrendMetric.SLEEP -> 100f
    }

    val unit = when (selectedMetric) {
        TrendMetric.RECOVERY -> "%"
        TrendMetric.STRAIN -> ""
        TrendMetric.SLEEP -> "%"
    }

    val formatValue = { value: Float ->
        if (selectedMetric == TrendMetric.STRAIN) {
            String.format("%.1f", value)
        } else {
            value.toInt().toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TrendMetric.values().forEach { metric ->
                val isSelected = selectedMetric == metric
                Text(
                    text = metric.label,
                    color = if (isSelected) metric.color else BaselTextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = BaselFontFamily,
                    modifier = Modifier.clickable { selectedMetric = metric }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(BaselSurfaceVariant)) {
            val fraction = 1f / TrendMetric.values().size
            val offset = fraction * selectedMetric.ordinal
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(if (offset > 0) offset else 0.001f))
                Box(modifier = Modifier.weight(fraction).height(2.dp).background(selectedMetric.color))
                val remaining = 1f - offset - fraction
                if (remaining > 0) {
                    Spacer(modifier = Modifier.weight(remaining))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(BaselSurface)
                .border(1.dp, BaselDivider, RoundedCornerShape(8.dp))
                .padding(24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text("${selectedMetric.label}\nTREND", color = BaselTextSecondary, fontSize = 10.sp, letterSpacing = 1.sp, fontFamily = BaselFontFamily)
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(formatValue(avgValue), color = BaselTextPrimary, fontSize = 48.sp, fontWeight = FontWeight.Bold, fontFamily = BaselNumberFontFamily)
                            Text("$unit AVG", color = selectedMetric.color, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp), fontFamily = BaselFontFamily)
                        }
                    }
                    
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(BaselSurfaceVariant)
                            .padding(4.dp)
                    ) {
                        TrendRange.values().forEach { range ->
                            val isSelected = selectedRange == range
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) BaselPrimaryDark else Color.Transparent)
                                    .clickable { selectedRange = range }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = range.label,
                                    color = if (isSelected) BaselTextPrimary else BaselTextSecondary,
                                    fontSize = 12.sp,
                                    fontFamily = BaselFontFamily
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.height(200.dp).padding(end = 8.dp, top = 16.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(formatValue(maxValue), color = BaselTextSecondary, fontSize = 10.sp, fontFamily = BaselNumberFontFamily)
                        Text(formatValue(maxValue * 0.75f), color = BaselTextSecondary, fontSize = 10.sp, fontFamily = BaselNumberFontFamily)
                        Text(formatValue(maxValue * 0.5f), color = BaselTextSecondary, fontSize = 10.sp, fontFamily = BaselNumberFontFamily)
                        Text(formatValue(maxValue * 0.25f), color = BaselTextSecondary, fontSize = 10.sp, fontFamily = BaselNumberFontFamily)
                    }
                    
                    LineChart(
                        data = mockData,
                        lineColor = selectedMetric.color,
                        minValue = minValue,
                        maxValue = maxValue,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TrendStatCard(modifier = Modifier.weight(1f), title = "AVERAGE", value = "${formatValue(avgValue)}$unit", color = BaselTextPrimary)
            TrendStatCard(modifier = Modifier.weight(1f), title = "BEST", value = "${formatValue(bestValue)}$unit", color = selectedMetric.color, borderColor = selectedMetric.color)
            TrendStatCard(modifier = Modifier.weight(1f), title = "WORST", value = "${formatValue(worstValue)}$unit", color = BaselRed, borderColor = BaselRed)
        }
    }
}

@Composable
fun TrendStatCard(modifier: Modifier = Modifier, title: String, value: String, color: Color, borderColor: Color = Color.Transparent) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(BaselSurface)
            .padding(1.dp)
            .background(borderColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(7.dp))
                .background(BaselSurface)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(title, color = BaselTextSecondary, fontSize = 10.sp, letterSpacing = 1.sp, fontFamily = BaselFontFamily)
                Spacer(modifier = Modifier.height(4.dp))
                Text(value, color = color, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = BaselNumberFontFamily)
            }
        }
    }
}
