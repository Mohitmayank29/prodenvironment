package com.mohit.proddevenvironmet.ApiCalls

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiInterface {
    @FormUrlEncoded
    @POST("dms_login")
    suspend fun getLogin(
//        @Field("v_name") v_name: String,
//        @Field("v_code") v_code: Int,
//        @Field("uname") uname: String,
//        @Field("pass") pass: String,
//        @Field("token") token: String
        @FieldMap body : Map<String,@JvmSuppressWildcards Any>
    ): Response<JsonObject>


    @Multipart
    @POST("retailer_sale_return")
     suspend fun retailer_sale_return(
        @PartMap data : Map<String,@JvmSuppressWildcards RequestBody>,
//        @Part("order_id") order_id: RequestBody?,
//        @Part("dealer_id") dealer_id: RequestBody?,
//        @Part("battery_status") battery_status: RequestBody?,
//        @Part("gps_status") gps_status: RequestBody?,
//        @Part("lat") lat: RequestBody?,
//        @Part("lng") lng: RequestBody?,
//        @Part("address") address: RequestBody?,
//        @Part("user_id") user_id: RequestBody?,
//        @Part("company_id") company_id: RequestBody?,
//        @Part("remarks") remarks: RequestBody?,
//        @Part("retailer_id") retailer_id: RequestBody?,
//        @Part("aganist_bill_no") aganist_bill_no: RequestBody?,
//        @Part("retailer_sale_return_details") retailer_sale_return_details: RequestBody?,
        @Part image_source: MultipartBody.Part?
    ): Response<JsonObject>
}