package com.alacritystudios.vortex.rest.retrofit.service

import androidx.lifecycle.LiveData
import com.alacritystudios.vortex.data.domain.StreamerToken
import com.alacritystudios.vortex.rest.adapter.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DefaultTwitchApiService {

    @Headers("Client-ID: kjto9y9iudkfaomqx6xieonymm7nty")
    @GET("channels/{streamerName}/access_token")
    fun getStreamerToken(
        @Path("streamerName") streamerName: String
    ): LiveData<ApiResponse<StreamerToken>>
}