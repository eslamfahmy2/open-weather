package com.testapp.data.networking.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
            //todo
        // Add API Key
        val url =
            req.url.newBuilder().addQueryParameter("key", "f00e24f03dc64ffeb1520012230303").build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}