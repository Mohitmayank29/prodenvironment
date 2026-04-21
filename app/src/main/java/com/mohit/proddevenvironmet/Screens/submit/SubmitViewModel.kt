package com.mohit.proddevenvironmet.Screens.submit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mohit.proddevenvironmet.ApiCalls.ApiInterface
import com.mohit.proddevenvironmet.ApiResponseHandler.ApiResult
import com.mohit.proddevenvironmet.CommonConstants.Constants
import com.mohit.proddevenvironmet.RoomDataBase.Table.ModuleEntity
import com.mohit.proddevenvironmet.RoomDataBase.Table.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SubmitViewModel @Inject constructor(
    private val api: ApiInterface,
    ) : ViewModel() {
    fun submitData(
        body: Map<String, RequestBody>,
        image: MultipartBody.Part?
    ) {
        viewModelScope.launch {

            val result = Constants.safeapicall {
                api.retailer_sale_return(body, image)
            }

            when (result) {
                is ApiResult.Success -> {
                    Log.d("TAG", "Success: ${result.data}")
                }

                is ApiResult.Error -> {
                    Log.d("TAG", "Error: ${result.message}")
                }
            }
        }
    }
}
