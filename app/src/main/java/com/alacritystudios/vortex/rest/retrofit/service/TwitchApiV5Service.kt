package com.alacritystudios.vortex.rest.retrofit.service

import com.alacritystudios.vortex.data.domain.GamesModelV5
import com.alacritystudios.vortex.data.domain.StreamsModelV5
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TwitchApiV5Service {

    @Headers("Client-ID: kjto9y9iudkfaomqx6xieonymm7nty")
    @GET("games/top")
    fun getTopGames(
        @Query("client_id") clientId: String,
        @Query("limit") limit: String, @Query("offset") offset: String?
    ): Call<GamesModelV5>

    @Headers("Client-ID: kjto9y9iudkfaomqx6xieonymm7nty")
    @GET("streams")
    fun getTopStreams(
        @Query("client_id") clientId: String, @Query("game") game: String?,
        @Query("limit") limit: String, @Query("offset") offset: String?
    ): Call<StreamsModelV5>
}