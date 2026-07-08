package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun StrainDetailScreen(viewModel: com.example.BaselViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val summary by viewModel.todaySummary.collectAsState()
    val strain = summary?.dayStrain ?: 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("DAY STRAIN", color = BaselTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
        Spacer(modifier = Modifier.height(32.dp))
        
        Box(
            modifier = Modifier.fillMaxWidth().aspectRatio(1.5f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 24.dp.toPx()
                val radius = (size.width - strokeWidth) / 2
                
                // Draw background arc (0 to 180 degrees -> 180 to 360 in Compose angles)
                drawArc(
                    color = BaselDivider,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(strokeWidth/2, strokeWidth/2),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                
                // Draw foreground arc (based on 0-21 scale)
                val sweep = (strain / 21f).coerceIn(0f, 1f) * 180f
                drawArc(
                    color = BaselPrimary,
                    startAngle = 180f,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(strokeWidth/2, strokeWidth/2),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 24.dp)) {
                Text(String.format("%.1f", strain), color = BaselTextPrimary, fontSize = 64.sp, fontWeight = FontWeight.Bold, fontFamily = BaselNumberFontFamily)
                Text("of 21.0 max", color = BaselTextSecondary, fontSize = 14.sp, fontFamily = BaselFontFamily)
            }
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
                Text("HR ZONE BREAKDOWN", color = BaselTextSecondary, fontSize = 12.sp, fontFamily = BaselFontFamily, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                ZoneRow("Zone 5 (90-100%)", "0 min", BaselRed)
                ZoneRow("Zone 4 (80-90%)", "12 min", BaselYellow)
                ZoneRow("Zone 3 (70-80%)", "45 min", BaselPrimary)
                ZoneRow("Zone 2 (60-70%)", "1h 30m", BaselGreen)
                ZoneRow("Zone 1 (50-60%)", "4h 15m", BaselTextSecondary)
            }
        }
    }
}

@Composable
fun ZoneRow(label: String, duration: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).clip(RoundedCornerShape(2.dp)).background(color))
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, color = BaselTextPrimary, fontSize = 14.sp, fontFamily = BaselFontFamily)
        }
        Text(duration, color = BaselTextSecondary, fontSize = 14.sp, fontFamily = BaselNumberFontFamily)
    }
}
