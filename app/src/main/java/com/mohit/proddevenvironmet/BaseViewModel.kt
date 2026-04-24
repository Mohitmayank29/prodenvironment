package com.mohit.proddevenvironmet

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.proddevenvironmet.NoInternet.NetworkObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

open class BaseViewModel(
    application: Application
) : ViewModel() {

    private val networkObserver = NetworkObserver(application)

    protected fun <T> autoRefresh(
        call: suspend () -> Unit
    ) {
        viewModelScope.launch {
            networkObserver.observe()
                .distinctUntilChanged()
                .collectLatest { isConnected ->
                    if (isConnected) {
                        call()
                    }
                }
        }
    }
}