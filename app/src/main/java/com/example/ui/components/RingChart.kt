package com.example.ui.components

import androidx.compose.foundation.layout.padding


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BaselFontFamily
import com.example.ui.theme.BaselSurfaceVariant
import com.example.ui.theme.BaselTextSecondary

@Composable
fun RingChart(
    value: Int,
    maxValue: Int = 100,
    color: Color,
    modifier: Modifier = Modifier,
    label: String = "RECOVERY",
    thickness: Float = 16f,
    isEstimated: Boolean = false
) {
    Box(modifier = modifier.aspectRatio(1f), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = BaselSurfaceVariant,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = thickness, cap = StrokeCap.Round)
            )
            
            val sweepAngle = (value.toFloat() / maxValue.toFloat()) * 360f
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = thickness, cap = StrokeCap.Round)
            )
        }
        
        androidx.compose.foundation.layout.Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value.toString(),
                color = Color.White,
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = BaselFontFamily,
                letterSpacing = (-2).sp
            )
            Text(
                text = label,
                color = color,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp,
                fontFamily = BaselFontFamily,
                modifier = Modifier.padding(top = 4.dp)
            )
            if (isEstimated) {
                Text(
                    text = "ESTIMATED (MISSING HRV)",
                    color = com.example.ui.theme.BaselYellow,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp,
                    fontFamily = BaselFontFamily,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

