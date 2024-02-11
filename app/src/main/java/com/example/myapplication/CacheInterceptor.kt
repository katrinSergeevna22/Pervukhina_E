package com.example.myapplication

import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        return response.newBuilder()
            .header("Cache-Control", "public, max-age=60")
            .build()
    }
}
