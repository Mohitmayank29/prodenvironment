package com.mohit.proddevenvironmet.ApiResponseHandler

sealed class ApiResult <T>{
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Error<T>(val message: String) : ApiResult<T>()
}