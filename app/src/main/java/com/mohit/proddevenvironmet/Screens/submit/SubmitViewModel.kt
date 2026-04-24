package com.mohit.proddevenvironmet.Screens.submit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.proddevenvironmet.ApiCalls.ApiInterface
import com.mohit.proddevenvironmet.ApiResponseHandler.ApiResult
import com.mohit.proddevenvironmet.CommonConstants.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class SubmitViewModel @Inject constructor(
    private val api: ApiInterface,
    ) : ViewModel() {
        val _state = MutableStateFlow<ApiResult<SubmitModel>?>(null)
        val state : StateFlow<ApiResult<SubmitModel>?> = _state
    fun submitData(
        body: Map<String, RequestBody>,
        image: MultipartBody.Part?
    ) {
        viewModelScope.launch {
            _state.value = ApiResult.Loading()
            val result = Constants.safeapicall<SubmitModel> {
                api.retailer_sale_return(body, image)
            }
              _state.value = result
//            when (result) {
//                is ApiResult.Success -> {
//                    Log.d("TAG", "Success: ${result.data}")
//                }

//                is ApiResult.Error -> {
//                    Log.d("TAG", "Error: ${result.message}")
//                }
//            }
        }
    }
}
