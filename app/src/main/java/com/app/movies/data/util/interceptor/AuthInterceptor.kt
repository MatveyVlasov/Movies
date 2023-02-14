package com.app.movies.data.util.interceptor

import com.app.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val url = chain.request().url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY).build()

        request.url(url)
        return chain.proceed(request.build())
    }
}
