package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun JournalScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaselBackground)
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Daily Check-in", color = BaselTextPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = BaselFontFamily)
        Spacer(modifier = Modifier.height(8.dp))
        Text("CAPTURE AMBIENT FACTORS", color = BaselTextSecondary, fontSize = 12.sp, letterSpacing = 2.sp, fontFamily = BaselFontFamily)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, BaselDivider, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Text("MODIFIERS", color = BaselTextSecondary, fontSize = 10.sp, letterSpacing = 1.sp, fontFamily = BaselFontFamily)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ModifierButton(text = "Alcohol", modifier = Modifier.weight(1f))
                    ModifierButton(text = "Caffeine", modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ModifierButton(text = "Late Meal", modifier = Modifier.weight(1f))
                    ModifierButton(text = "Stress", modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ModifierButton(text = "Illness", modifier = Modifier.weight(1f))
                    ModifierButton(text = "Travel", modifier = Modifier.weight(1f))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, BaselDivider, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Text("SIGNAL NOTES", color = BaselTextSecondary, fontSize = 10.sp, letterSpacing = 1.sp, fontFamily = BaselFontFamily)
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Enter contextual free-text...", fontFamily = BaselFontFamily, color = BaselTextSecondary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BaselSurfaceVariant),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = BaselSurfaceVariant,
                        unfocusedContainerColor = BaselSurfaceVariant,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = BaselTextPrimary
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            com.example.ui.components.BaselButton(
                text = "SAVE ENTRY",
                onClick = { /*TODO*/ },
                isPrimary = true
            )
        }
    }
}

@Composable
fun ModifierButton(text: String, modifier: Modifier = Modifier) {
    com.example.ui.components.BaselOutlinedButton(
        text = text,
        onClick = { /* Toggle */ },
        modifier = modifier
    )
}
