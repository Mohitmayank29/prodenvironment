package com.mohit.proddevenvironmet.NoInternet

import android.content.Context
import com.mohit.proddevenvironmet.CommonConstants.Constants
import javax.inject.Inject

class NetworkHelper @Inject constructor(
       private  val context: Context
) {
    fun isNetworkConnected(): Boolean {
        return Constants.NetworkUtils.isInternetAvailable(context)
    }
}