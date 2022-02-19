package com.joellui.ryuu.utils

import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val response = chain.proceed(request)

        val data = response.body()

        if (data != null) {

            val m = response.peekBody(Long.MAX_VALUE).string()
            val jsonObject: JsonObject = JsonParser().parse(m).asJsonObject;
            val s = jsonObject.get("status_code").asInt

            if (s == 400 || s == 401 || s == 403 || s == 404 || s == 429 || s == 500) {
                Log.d("Response", "api check 404-> $s")
                Log.d("Response", "jsonObject-> $jsonObject")

                val newResponse =
                    response.newBuilder().message(jsonObject.get("message").toString()).code(404)
                        .build();

                val jsonObj: JsonObject =
                    JsonParser().parse(newResponse.peekBody(Long.MAX_VALUE).string()).asJsonObject
                Log.d("Response", "jsonObject new response -> $jsonObj")

                return newResponse
            }
        }



        return response
    }
}