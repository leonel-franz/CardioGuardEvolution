package com.example.cardioguardevolution

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.cardioguardevolution.navigation.AppNavHost
import com.example.cardioguardevolution.ui.theme.CardioGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardioGuardTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}
