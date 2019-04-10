package com.alacritystudios.vortex.dependency.module

import com.alacritystudios.vortex.data.repository.TwitchRepository
import com.alacritystudios.vortex.rest.retrofit.service.DefaultTwitchApiService
import com.alacritystudios.vortex.rest.retrofit.service.NewTwitchApiService
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesTwitchRepository(newTwitchApiService: NewTwitchApiService, defaultTwitchApiService: DefaultTwitchApiService): TwitchRepository {
        return TwitchRepository(newTwitchApiService, defaultTwitchApiService)
    }
}
