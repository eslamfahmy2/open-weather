package com.testapp.data.networking.interceptor

import com.testapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // Add API Key
        val url = req.url.newBuilder().addQueryParameter("key", BuildConfig.apiKey).build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}