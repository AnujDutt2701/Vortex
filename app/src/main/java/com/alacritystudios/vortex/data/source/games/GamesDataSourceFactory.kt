package com.alacritystudios.vortex.data.source.games

import androidx.paging.DataSource
import androidx.lifecycle.MutableLiveData
import com.alacritystudios.vortex.data.domain.GamesModelV5
import com.alacritystudios.vortex.rest.retrofit.service.TwitchApiV5Service


class GamesDataSourceFactory : DataSource.Factory<String, GamesModelV5.Top> {

    private lateinit var gamesDataSource2LiveData: MutableLiveData<GamesDataSource>
    private lateinit var gamesDataSource: GamesDataSource
    internal var twitchApiV5Service: TwitchApiV5Service

    constructor(newTwitchApiService: TwitchApiV5Service) : super() {
        this.twitchApiV5Service = newTwitchApiService
        this.gamesDataSource2LiveData = MutableLiveData<GamesDataSource>()
    }

    override fun create(): DataSource<String, GamesModelV5.Top> {
        gamesDataSource = GamesDataSource(twitchApiV5Service)
        gamesDataSource2LiveData.postValue(gamesDataSource)
        return gamesDataSource
    }

    fun getGamesDataSource2LiveData(): MutableLiveData<GamesDataSource> {
        return gamesDataSource2LiveData
    }
}