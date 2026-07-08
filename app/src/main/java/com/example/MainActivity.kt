package com.example
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.border
import androidx.compose.foundation.background



import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.BaselTheme
import com.example.ui.theme.BaselBackground
import com.example.ui.theme.BaselGreen
import com.example.ui.theme.BaselSurface
import com.example.ui.theme.BaselTextPrimary
import com.example.ui.theme.BaselTextSecondary
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.TrendsScreen
import com.example.ui.screens.JournalScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.DebugScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      BaselTheme {
        BaselApp()
      }
    }
  }
}

@Composable
fun BaselApp() {
    val navController = rememberNavController()
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BaselBackground,
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(32.dp))
                        .background(BaselSurface)
                        .border(1.dp, com.example.ui.theme.BaselDivider, androidx.compose.foundation.shape.RoundedCornerShape(32.dp))
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    NavItem(
                        icon = Icons.Default.Home,
                        label = "HOME",
                        selected = currentRoute == "home",
                        onClick = {
                            navController.navigate("home") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                    NavItem(
                        icon = Icons.Default.Timeline,
                        label = "TRENDS",
                        selected = currentRoute == "trends",
                        onClick = {
                            navController.navigate("trends") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                    NavItem(
                        icon = Icons.Default.Edit,
                        label = "JOURNAL",
                        selected = currentRoute == "journal",
                        onClick = {
                            navController.navigate("journal") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                    NavItem(
                        icon = Icons.Default.Settings,
                        label = "SETTINGS",
                        selected = currentRoute == "settings",
                        onClick = {
                            navController.navigate("settings") {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("trends") { TrendsScreen() }
            composable("journal") { JournalScreen() }
            composable("settings") { 
                SettingsScreen(
                    onNavigateToDebug = { navController.navigate("debug") },
                    onNavigateToBaselAge = { navController.navigate("basel_age") },
                    onNavigateToMonthlyAssessment = { navController.navigate("monthly_assessment") }
                ) 
            }
            composable("debug") { DebugScreen() }
            composable("basel_age") { com.example.ui.screens.BaselAgeScreen() }
            composable("monthly_assessment") { com.example.ui.screens.MonthlyAssessmentScreen() }
        }
    }
}

@Composable
fun NavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    androidx.compose.foundation.layout.Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = Modifier.clickable(
            interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource(),
            indication = null,
            onClick = onClick
        ).padding(4.dp)
    ) {
        val color = if (selected) Color.White else com.example.ui.theme.BaselTextSecondary
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = color,
            fontSize = 9.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            letterSpacing = 1.sp,
            fontFamily = com.example.ui.theme.BaselFontFamily
        )
    }
}