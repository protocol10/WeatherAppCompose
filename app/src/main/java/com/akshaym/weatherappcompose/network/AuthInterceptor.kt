package com.akshaym.weatherappcompose.network

import com.akshaym.weatherappcompose.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Force the API Key into the URL query parameters
        val url = originalUrl.newBuilder()
            .build()

        Timber.i("Bearer ${BuildConfig.ACCUWEATHER_API_KEY}")
        val request = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.ACCUWEATHER_API_KEY}")
            .url(url)
            .build()

        Timber.i("requerst ${request.toCurl()}")
        return chain.proceed(request)
    }
}