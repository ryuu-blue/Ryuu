package com.joellui.ryuu.api

import com.joellui.ryuu.utils.Constants.Companion.BASE_URL
import com.joellui.ryuu.utils.ErrorInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {
    private val retrofit by lazy {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        clientBuilder.readTimeout(15, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(15, TimeUnit.SECONDS);

        val okHttpClient = clientBuilder.addInterceptor(ErrorInterceptor()).build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val animeApi : AniApi by lazy {
        retrofit.create(AniApi::class.java)
    }
}