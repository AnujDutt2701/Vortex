package com.alacritystudios.vortex.dependency.module

import com.alacritystudios.vortex.dependency.scope.VortexScope
import com.alacritystudios.vortex.rest.adapter.LiveDataCallAdapterFactory
import com.alacritystudios.vortex.rest.retrofit.service.DefaultTwitchApiService
import com.alacritystudios.vortex.rest.retrofit.service.NewTwitchApiService
import com.alacritystudios.vortex.rest.retrofit.service.TwitchApiV5Service
import com.alacritystudios.vortex.util.ConstantsUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * @author Anuj Dutt
 *         Module to provide methods for making API calls to Twitch.
 */

@Module
class TwitchApiModule {

    @VortexScope
    @Provides
    @Named("New")
    internal fun providesNewTwitchApiRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstantsUtil.NEW_TWITCH_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @VortexScope
    @Provides
    @Named("V5")
    internal fun providesTwitchApiV5RetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstantsUtil.TWITCH_API_V5_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @VortexScope
    @Provides
    @Named("Default")
    internal fun providesDefaultTwitchApiRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ConstantsUtil.TWITCH_API_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @VortexScope
    @Provides
    internal fun providesNewTwitchApiService(@Named("New") retrofit: Retrofit): NewTwitchApiService {
        return retrofit.create(NewTwitchApiService::class.java)
    }

    @VortexScope
    @Provides
    internal fun providesTwitchApiV5Service(@Named("V5") retrofit: Retrofit): TwitchApiV5Service {
        return retrofit.create(TwitchApiV5Service::class.java)
    }

    @VortexScope
    @Provides
    internal fun providesDefaultTwitchApiService(@Named("Default") retrofit: Retrofit): DefaultTwitchApiService {
        return retrofit.create(DefaultTwitchApiService::class.java)
    }
}