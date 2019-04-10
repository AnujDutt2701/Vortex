package com.alacritystudios.vortex.dependency.module

import com.alacritystudios.vortex.dependency.scope.VortexScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @VortexScope
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {

            override fun log(message: String) {
                Timber.d(message)
            }
        })
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return httpLoggingInterceptor
    }

    @VortexScope
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}