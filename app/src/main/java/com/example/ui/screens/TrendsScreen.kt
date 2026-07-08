package com.example.ui.screens
import androidx.compose.foundation.border


import androidx.compose.foundation.Canvas
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
import com.example.ui.theme.*

@Composable
fun TrendsScreen() {
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
            Text("RECOVERY", color = BaselPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
            Text("STRAIN", color = BaselTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
            Text("SLEEP", color = BaselTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
            Text("HRV", color = BaselTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(BaselSurfaceVariant)) {
            Box(modifier = Modifier.fillMaxWidth(0.25f).height(2.dp).background(BaselPrimary))
        }
        
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text("RECOVERY\nTREND", color = BaselTextSecondary, fontSize = 10.sp, letterSpacing = 1.sp, fontFamily = BaselFontFamily)
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("68", color = BaselTextPrimary, fontSize = 48.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
                            Text("% AVG", color = BaselGreen, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp), fontFamily = BaselFontFamily)
                        }
                    }
                    
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(BaselSurfaceVariant)
                            .padding(4.dp)
                    ) {
                        Text("7D", color = BaselTextSecondary, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontFamily = BaselFontFamily)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(BaselPrimaryDark)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("30D", color = BaselTextPrimary, fontSize = 12.sp, fontFamily = BaselFontFamily)
                        }
                        Text("90D", color = BaselTextSecondary, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontFamily = BaselFontFamily)
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Placeholder for chart
                Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    // Axis labels
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("100", color = BaselTextSecondary, fontSize = 10.sp, fontFamily = BaselFontFamily)
                        Text("75", color = BaselTextSecondary, fontSize = 10.sp, fontFamily = BaselFontFamily)
                        Text("50", color = BaselTextSecondary, fontSize = 10.sp, fontFamily = BaselFontFamily)
                        Text("25", color = BaselTextSecondary, fontSize = 10.sp, fontFamily = BaselFontFamily)
                    }
                    
                    // Chart line simulation
                    Canvas(modifier = Modifier.fillMaxSize().padding(start = 24.dp, bottom = 24.dp)) {
                        // Drawing logic for chart goes here
                        drawLine(
                            color = BaselGreen,
                            start = androidx.compose.ui.geometry.Offset(0f, size.height * 0.6f),
                            end = androidx.compose.ui.geometry.Offset(size.width, size.height * 0.4f),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TrendStatCard(modifier = Modifier.weight(1f), title = "AVERAGE", value = "68%", color = BaselTextPrimary)
            TrendStatCard(modifier = Modifier.weight(1f), title = "BEST", value = "92%", color = BaselGreen, borderColor = BaselGreen)
            TrendStatCard(modifier = Modifier.weight(1f), title = "WORST", value = "34%", color = BaselRed, borderColor = BaselRed)
        }
    }
}

@Composable
fun TrendStatCard(modifier: Modifier = Modifier, title: String, value: String, color: Color, borderColor: Color = Color.Transparent) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(BaselSurface)
            .padding(1.dp) // Border thickness
            .background(borderColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(23.dp))
                .background(BaselSurface)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(title, color = BaselTextSecondary, fontSize = 10.sp, letterSpacing = 1.sp, fontFamily = BaselFontFamily)
                Spacer(modifier = Modifier.height(4.dp))
                Text(value, color = color, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
            }
        }
    }
}
