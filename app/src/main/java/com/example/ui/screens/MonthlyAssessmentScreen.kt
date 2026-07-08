package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun MonthlyAssessmentScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("MONTHLY ASSESSMENT", color = BaselTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
        Text("October 2026", color = BaselTextSecondary, fontSize = 14.sp, fontFamily = BaselFontFamily)
        Spacer(modifier = Modifier.height(24.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(BaselSurface)
                .border(1.dp, BaselDivider.copy(alpha=0.5f), RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column {
                Text("PERFORMANCE SUMMARY", color = BaselTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
                Spacer(modifier = Modifier.height(16.dp))
                
                AssessmentRow("Avg Recovery", "74", BaselGreen)
                AssessmentRow("Avg Strain", "12.4", BaselPrimary)
                AssessmentRow("Sleep Consistency", "82%", BaselTextPrimary)
                AssessmentRow("Basel Age Delta", "-1 yr", BaselGreen)
            }
        }
    }
}

@Composable
fun AssessmentRow(label: String, value: String, valueColor: androidx.compose.ui.graphics.Color) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = BaselTextPrimary, fontSize = 14.sp, fontFamily = BaselFontFamily)
        Text(value, color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
    }
}
