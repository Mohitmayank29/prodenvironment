package com.mohit.proddevenvironmet.ApiCalls

import com.google.gson.GsonBuilder
import com.mohit.proddevenvironmet.BuildConfig
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object ApiClient {
//    var BASE_URL:String="https://demo.msell.in/public/api/"
    var BASE_URL = BuildConfig.BASE_URL
    var WEBSERVICEURL:String="https://demo.msell.in/demo_api/webservices/"


var client = OkHttpClient.Builder()
    .connectTimeout(7000, TimeUnit.SECONDS)
    .readTimeout(7000, TimeUnit.SECONDS).build()

    var retrofit : Retrofit? = null
    val gson = GsonBuilder()
        .setLenient()
        .create()
    fun getClient(): Retrofit {
        if(retrofit != null)
            retrofit = null
         retrofit = Retrofit.Builder()
             .baseUrl(BASE_URL)
             .client(client)
             .addConverterFactory(GsonConverterFactory.create(gson))
             .build()
        return retrofit as Retrofit

    }
    fun getClient1(url : String): Retrofit {
        if (retrofit !=null)
            retrofit =null

        retrofit = Retrofit.Builder()
            .baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit as Retrofit
    }

    fun getWebServiceClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(WEBSERVICEURL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build() as Retrofit
    }



}