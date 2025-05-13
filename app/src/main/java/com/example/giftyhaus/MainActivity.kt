package com.example.giftyhaus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.NavHostController


import com.example.giftyhaus.navigation.AppNavHost
import com.example.giftyhaus.ui.theme.screens.bars.TopBarUI


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            AppNavHost()


        }
    }
}
