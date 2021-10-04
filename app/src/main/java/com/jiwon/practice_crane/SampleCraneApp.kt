package com.jiwon.practice_crane

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.jiwon.practice_crane.util.UnsplashSizingInterceptor

class SampleCraneApp : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .componentRegistry {
                add(UnsplashSizingInterceptor)
            }
            .build()
    }
}