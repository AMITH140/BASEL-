package com.example.ui.screens
import androidx.compose.foundation.clickable


import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
fun SettingsScreen(
    onNavigateToDebug: () -> Unit = {},
    onNavigateToBaselAge: () -> Unit = {},
    onNavigateToMonthlyAssessment: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("System Configuration", color = BaselTextPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Manage device telemetry and clinical baselines.", color = BaselTextSecondary, fontSize = 12.sp, fontFamily = BaselFontFamily)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            com.example.ui.components.BaselButton(text = "Basel Age", onClick = onNavigateToBaselAge, modifier = Modifier.weight(1f))
            com.example.ui.components.BaselButton(text = "Monthly Report", onClick = onNavigateToMonthlyAssessment, modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(BaselSurface).border(1.dp, BaselDivider.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("DATA SOURCE", color = BaselTextSecondary, fontSize = 10.sp, letterSpacing = 1.sp, fontFamily = BaselFontFamily)
                    Row(
                        modifier = Modifier
                            .border(1.dp, BaselGreen, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(androidx.compose.foundation.shape.CircleShape).background(BaselGreen))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Active", color = BaselGreen, fontSize = 12.sp, fontFamily = BaselFontFamily)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Health\nConnect", color = BaselTextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily, lineHeight = 28.sp)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Column {
                    Text("Last synchronized: 14:03:22 UTC", color = BaselTextSecondary, fontSize = 12.sp, fontFamily = BaselFontFamily)
                    Spacer(modifier = Modifier.height(16.dp))
                    com.example.ui.components.BaselButton(text = "Force Sync", onClick = { /* Force sync */ }, isPrimary = true)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(BaselSurface).border(1.dp, BaselDivider.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column {
                Text("PERSONAL BASELINE", color = BaselTextSecondary, fontSize = 10.sp, letterSpacing = 1.sp, fontFamily = BaselFontFamily)
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("8", color = BaselTextPrimary, fontSize = 48.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
                    Text("h ", color = BaselTextSecondary, fontSize = 24.sp, modifier = Modifier.padding(bottom = 6.dp), fontFamily = BaselFontFamily)
                    Text("15", color = BaselTextPrimary, fontSize = 48.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
                    Text("m", color = BaselTextSecondary, fontSize = 24.sp, modifier = Modifier.padding(bottom = 6.dp), fontFamily = BaselFontFamily)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Calculated Sleep Need", color = BaselTextSecondary, fontSize = 14.sp, fontFamily = BaselFontFamily)
                Spacer(modifier = Modifier.height(24.dp))
                com.example.ui.components.BaselOutlinedButton(text = "Recalculate Model", onClick = { /* Recalculate Model */ })
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        com.example.ui.components.BaselOutlinedButton(text = "⚙ Initialize Debug Mode", onClick = onNavigateToDebug)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

