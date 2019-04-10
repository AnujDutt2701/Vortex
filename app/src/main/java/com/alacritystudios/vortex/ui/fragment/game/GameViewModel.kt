package com.alacritystudios.vortex.ui.fragment.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alacritystudios.vortex.data.domain.StreamsModelV5
import com.alacritystudios.vortex.data.source.streams.StreamsDataSourceFactory
import com.alacritystudios.vortex.rest.retrofit.service.TwitchApiV5Service
import com.alacritystudios.vortex.util.HttpResponseUtil
import timber.log.Timber
import javax.inject.Inject

class GameViewModel : ViewModel {

    internal var twitchApiV5Service: TwitchApiV5Service

    internal lateinit var streamsLiveData: LiveData<PagedList<StreamsModelV5.Stream>>

    internal lateinit var responseStateLiveData: LiveData<HttpResponseUtil>

    @Inject
    constructor(newTwitchApiService: TwitchApiV5Service) : super() {
        this.twitchApiV5Service = newTwitchApiService
    }

    fun initiateFind(game: String) {
        Timber.d("initiateFind")
        val gamesDataSourceFactory2 = StreamsDataSourceFactory(twitchApiV5Service, game)

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(20)
            .setPageSize(20).build()

        streamsLiveData = LivePagedListBuilder(gamesDataSourceFactory2, config).build()
        responseStateLiveData = Transformations.switchMap(gamesDataSourceFactory2.getStreamsDataSourceLiveData()) {
            it.getResponseState()
        }
    }

    fun refresh() {
        Timber.d("refresh")
        streamsLiveData.value?.dataSource?.invalidate()
    }
}