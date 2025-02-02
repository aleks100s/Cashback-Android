package com.alextos.cashback.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alextos.cashback.app.navigation.ApplicationRoot
import com.alextos.cashback.app.theme.CashbackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashbackTheme {
                ApplicationRoot()
            }
        }
    }
}