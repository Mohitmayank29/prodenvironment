package com.mohit.proddevenvironmet.SessionHandler

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.mohit.proddevenvironmet.MainActivity
import com.mohit.proddevenvironmet.CommonConstants.SessionManager

@Composable
fun SessionHandler() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("SessionTest", "ON_PAUSE called")
                    SessionManager.lastBackgroundTime = System.currentTimeMillis()
                }

                Lifecycle.Event.ON_RESUME -> {
                    Log.d("SessionTest", "ON_RESUME called")
                    val diff = System.currentTimeMillis() - SessionManager.lastBackgroundTime
                    Log.d("SessionTest", "Time diff: $diff")
                    if (SessionManager.lastBackgroundTime != 0L && diff > 10_000) {
                        Log.d("SessionTest", "Restart triggered")

                        val intent = Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }

                        context.startActivity(intent)
                        SessionManager.lastBackgroundTime = 0L

                    }
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}