package com.alacritystudios.vortex.data.repository

import androidx.lifecycle.LiveData
import com.alacritystudios.vortex.data.domain.StreamerToken
import com.alacritystudios.vortex.rest.adapter.ApiResponse
import com.alacritystudios.vortex.rest.retrofit.service.DefaultTwitchApiService
import com.alacritystudios.vortex.rest.retrofit.service.NewTwitchApiService
import javax.inject.Inject

class TwitchRepository {

    internal var newTwitchApiService: NewTwitchApiService
    internal var defaultTwitchApiService: DefaultTwitchApiService

    @Inject
    constructor(newTwitchApiService: NewTwitchApiService, defaultTwitchApiService: DefaultTwitchApiService) {
        this.newTwitchApiService = newTwitchApiService
        this.defaultTwitchApiService = defaultTwitchApiService
    }

    public fun getStreamerToken(streamerName: String): LiveData<ApiResponse<StreamerToken>> {
        return defaultTwitchApiService.getStreamerToken(streamerName)
    }
}