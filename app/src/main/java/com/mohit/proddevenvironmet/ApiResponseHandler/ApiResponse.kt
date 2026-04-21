package com.mohit.proddevenvironmet.ApiResponseHandler

data class ApiResponse<T>(
    val response: Boolean,
    val message: String,
    val details: T?
)