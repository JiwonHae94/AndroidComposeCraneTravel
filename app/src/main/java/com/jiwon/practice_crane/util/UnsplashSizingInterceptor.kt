package com.jiwon.practice_crane.util

import coil.annotation.ExperimentalCoilApi
import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.size.PixelSize
import okhttp3.HttpUrl


@OptIn(ExperimentalCoilApi::class)
object UnsplashSizingInterceptor : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val data = chain.request.data
        val size = chain.size
        if (data is String &&
            data.startsWith("https://images.unsplash.com/photo-") &&
            size is PixelSize
        ) {
            val url = HttpUrl.get(data)
                .newBuilder()
                .addQueryParameter("w", size.width.toString())
                .addQueryParameter("h", size.height.toString())
                .build()
            val request = chain.request.newBuilder().data(url).build()
            return chain.proceed(request)
        }
        return chain.proceed(chain.request)
    }
}
