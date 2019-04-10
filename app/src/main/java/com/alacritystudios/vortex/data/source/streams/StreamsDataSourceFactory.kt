package com.alacritystudios.vortex.data.source.streams

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.alacritystudios.vortex.data.domain.StreamsModelV5
import com.alacritystudios.vortex.rest.retrofit.service.TwitchApiV5Service

class StreamsDataSourceFactory : DataSource.Factory<String, StreamsModelV5.Stream> {

    private var streamsDataSourceLiveData: MutableLiveData<StreamsDataSource>
    private var game: String
    private lateinit var streamsDataSource: StreamsDataSource
    internal var twitchApiV5Service: TwitchApiV5Service

    constructor(newTwitchApiService: TwitchApiV5Service, game: String) : super() {
        this.twitchApiV5Service = newTwitchApiService
        this.game = game
        this.streamsDataSourceLiveData = MutableLiveData<StreamsDataSource>()
    }

    override fun create(): DataSource<String, StreamsModelV5.Stream> {
        streamsDataSource = StreamsDataSource(twitchApiV5Service, game)
        streamsDataSourceLiveData.postValue(streamsDataSource)
        return streamsDataSource
    }

    fun getStreamsDataSourceLiveData(): MutableLiveData<StreamsDataSource> {
        return streamsDataSourceLiveData
    }
}