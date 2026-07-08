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
import com.example.domain.BaselAgeAlgorithm
import com.example.ui.theme.*

@Composable
fun BaselAgeScreen() {
    val result = BaselAgeAlgorithm.estimate(30, 52f, 45f, 75f, 80f)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("BASEL AGE", color = BaselTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
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
                Text("ESTIMATED BIOLOGICAL AGE", color = BaselTextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = androidx.compose.ui.Alignment.Bottom) {
                    Text("${result.age}", color = BaselTextPrimary, fontSize = 64.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
                    Text(" yrs", color = BaselTextSecondary, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily, modifier = Modifier.padding(bottom = 12.dp))
                }
                
                val deltaSign = if (result.delta > 0) "+" else if (result.delta < 0) "" else ""
                val deltaColor = if (result.delta > 0) BaselRed else if (result.delta < 0) BaselGreen else BaselTextSecondary
                
                Text("Chronological Age: 30", color = BaselTextSecondary, fontSize = 14.sp, fontFamily = BaselFontFamily)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Delta: $deltaSign${result.delta} years", color = deltaColor, fontSize = 14.sp, fontWeight = FontWeight.Medium, fontFamily = BaselFontFamily)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(result.drivingFactor, color = BaselTextPrimary, fontSize = 14.sp, fontFamily = BaselFontFamily)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("DISCLAIMER: Basel Age is a wellness estimate based on your biometric trends (HRV, RHR, Sleep Consistency). It is not a medical measurement.", color = BaselTextSecondary, fontSize = 10.sp, fontFamily = BaselFontFamily, lineHeight = 14.sp)
    }
}
