package com.alacritystudios.vortex.rest.retrofit.service

import com.alacritystudios.vortex.data.domain.GamesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewTwitchApiService {

    @Headers("Client-ID: kjto9y9iudkfaomqx6xieonymm7nty")
    @GET("games/top")
    fun getTopGames(
        @Query("first") first: String, @Query("before") before: String,
        @Query("after") after: String
    ): Call<GamesModel>
}