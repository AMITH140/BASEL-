package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@Composable
fun DebugScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("RAW_DATA_STREAM", color = BaselTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text("TIMESTAMP", color = BaselTextSecondary, modifier = Modifier.weight(1f), fontSize = 10.sp, fontFamily = BaselFontFamily)
            Text("TYPE", color = BaselTextSecondary, modifier = Modifier.weight(1f), fontSize = 10.sp, fontFamily = BaselFontFamily)
            Text("VALUE", color = BaselTextSecondary, modifier = Modifier.weight(1f), fontSize = 10.sp, fontFamily = BaselFontFamily)
        }
        
        DebugRow("1698412800000", "HEART_RATE", "62.5 BPM", BaselYellow)
        DebugRow("1698412860000", "HEART_RATE", "64.1 BPM", BaselYellow)
        DebugRow("1698412920000", "HRV_SDNN", "45.2 MS", BaselGreen)
        DebugRow("1698413100000", "SLEEP_STAGE", "DEEP", SleepDeep)
        DebugRow("1698413400000", "SLEEP_STAGE", "REM", SleepREM)
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text("ALGORITHM WEIGHTS", color = BaselTextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Recovery: HRV (40%) | RHR (30%) | Sleep (30%)", color = BaselTextSecondary, fontSize = 12.sp, fontFamily = BaselFontFamily)
        Text("Fallback: RHR (40%) | Sleep (60%)", color = BaselTextSecondary, fontSize = 12.sp, fontFamily = BaselFontFamily)
        Text("Sleep Perf: Duration (70%) | Consistency (30%)", color = BaselTextSecondary, fontSize = 12.sp, fontFamily = BaselFontFamily)
    }
}

@Composable
fun DebugRow(timestamp: String, type: String, value: String, valueColor: androidx.compose.ui.graphics.Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BaselSurface)
            .padding(12.dp)
    ) {
        Text(timestamp, color = BaselTextPrimary, modifier = Modifier.weight(1f), fontSize = 10.sp, fontFamily = BaselFontFamily)
        Text(type, color = BaselPrimary, modifier = Modifier.weight(1f), fontSize = 10.sp, fontFamily = BaselFontFamily)
        Text(value, color = valueColor, modifier = Modifier.weight(1f), fontSize = 10.sp, fontFamily = BaselFontFamily)
    }
}
