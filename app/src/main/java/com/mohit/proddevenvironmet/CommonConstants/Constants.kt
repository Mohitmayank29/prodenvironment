package com.mohit.proddevenvironmet.CommonConstants

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohit.proddevenvironmet.ApiResponseHandler.ApiResult
import com.mohit.proddevenvironmet.NoInternet.NoInternetException
import com.mohit.proddevenvironmet.R
import retrofit2.Response
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object Constants {
    private const val TAG = "FileFlow"
    suspend fun <T> safeapicall(
        apicall :suspend () -> Response<T>

    ) : ApiResult<T> {
        return try {
            val response = apicall()
            Log.d("API_URL", response.raw().request.url.toString())
            Log.d("API_RESPONSE", response.body().toString())
            Log.d("API_RESPONSE", response.code().toString())
            Log.d("API_RESPONSE", response.message().toString())
            val body = response.body()

            if (response.isSuccessful && response.body() != null) {
//                Log.d("response",)
                if (response.code() == 200) {
                    ApiResult.Success(response.body())
                }else {
                    ApiResult.Error(response.message())
                }
            }
            else{
                val errorMessage = try {
                    response.errorBody()?.string() ?: "Unknown error"
                } catch (e: Exception) {
                    "Error parsing error body"
                }
              when (response.code()) {
                  400 -> ApiResult.Error("Bad request : $errorMessage")

                  401 -> ApiResult.Error("Unauthorized (Login issue)")

                  403 -> ApiResult.Error("Forbidden (No permission)")

                  404 -> ApiResult.Error("API Not Found")

                  500 -> ApiResult.Error("Server Error (Try again later)")

                  else -> ApiResult.Error("Error ${response.code()}: $errorMessage")
              }

            }
        }
        catch (e : Exception){
            ApiResult.Error(
                when (e) {
                    is NoInternetException -> "No Internet Connection"
                    is SocketTimeoutException -> "Timeout! Check internet"
                    is UnknownHostException -> "No Internet Connection"
                    else -> e.localizedMessage ?: "Something went wrong"
                }
            )
        }
    }
    object NetworkUtils {

        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }
//    @Composable
//    fun CustomToast(
//        message: String,
//        type: Int) {
//        val icon = when (type) {
//            0 -> R.drawable.ic_launcher_foreground
//            1 -> R.drawable.ic_launcher_foreground
//            2 -> R.drawable.ic_launcher_foreground
//            else -> R.drawable.ic_launcher_foreground
//        }
//        Row( Modifier
//                .padding(16.dp)
//                .background(Color.Black, shape = RoundedCornerShape(12.dp))
//                .padding(horizontal = 16.dp, vertical = 10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Image(
//                painter = painterResource(id = icon),
//                contentDescription = null,
//                modifier = Modifier.size(24.dp)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(
//                text = message,
//                color = Color.White,
//                fontSize = 14.sp
//            )
//        }
//    }
}

object SessionManager {
    var lastBackgroundTime: Long = 0L

//    private const val PREF_NAME = "session_pref"
//    private const val KEY_TIME = "last_time"
//
//    fun saveTime(context: Context) {
//        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        pref.edit().putLong(KEY_TIME, System.currentTimeMillis()).apply()
//    }
//
//    fun getTime(context: Context): Long {
//        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        return pref.getLong(KEY_TIME, 0L)
//    }
}