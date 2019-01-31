package com.nigamar.contactswithotpsending.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nigamar.contactswithotpsending.data.network.response.NexmoResponse
import com.nigamar.contactswithotpsending.internals.API_KEY
import com.nigamar.contactswithotpsending.internals.API_SECRET
import com.nigamar.contactswithotpsending.internals.BASE_URL
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NexmoSmsService {
    @FormUrlEncoded
    @POST("sms/json")
    fun sendSms(@Field("from") from:String,@Field("to") to:String,@Field("text")text:String):Deferred<NexmoResponse>

    companion object {
        fun createService():NexmoSmsService {
            val requestInterceptor= Interceptor{chain->
                val url= chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .addQueryParameter("api_secret", API_SECRET)
                    .build()
                val request= chain.request().newBuilder().url(url).build()
                return@Interceptor chain.proceed(request)
            }
            val client= OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            return retrofit.create(NexmoSmsService::class.java)
        }
    }
}