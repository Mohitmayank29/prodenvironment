package com.mohit.proddevenvironmet.NoInternet

import android.content.Context
import com.mohit.proddevenvironmet.CommonConstants.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    @param:ApplicationContext val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!Constants.NetworkUtils.isInternetAvailable(context)) {
            throw NoInternetException("No Internet Connection")
        }

        return chain.proceed(chain.request())
    }
}