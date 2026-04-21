package com.mohit.proddevenvironmet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mohit.proddevenvironmet.Screens.MainScreen.MainScreen
import com.mohit.proddevenvironmet.Screens.submit.SubmitScreen
import com.mohit.proddevenvironmet.SessionHandler.SessionHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SessionHandler()
            SubmitScreen()
//            MainScreen()
        }
    }
}