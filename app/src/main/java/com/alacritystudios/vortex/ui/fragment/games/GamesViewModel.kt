package com.alacritystudios.vortex.ui.fragment.games

import androidx.lifecycle.*
import androidx.paging.PagedList
import timber.log.Timber
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.lifecycle.Transformations
import com.alacritystudios.vortex.data.domain.GamesModelV5
import com.alacritystudios.vortex.data.source.games.GamesDataSourceFactory
import com.alacritystudios.vortex.rest.retrofit.service.TwitchApiV5Service
import com.alacritystudios.vortex.util.HttpResponseUtil

class GamesViewModel : ViewModel {

    internal var twitchApiV5Service: TwitchApiV5Service

    internal lateinit var gamesPagedListLiveData: LiveData<PagedList<GamesModelV5.Top>>

    internal lateinit var responseStateLiveData: LiveData<HttpResponseUtil>

    @Inject
    constructor(newTwitchApiService: TwitchApiV5Service) : super() {
        this.twitchApiV5Service = newTwitchApiService
    }

    fun initiateFind() {
        Timber.d("initiateFind")
        val gamesDataSourceFactory2 = GamesDataSourceFactory(twitchApiV5Service)

        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(20)
            .setPageSize(20).build()

        gamesPagedListLiveData = LivePagedListBuilder(gamesDataSourceFactory2, config).build()
        responseStateLiveData = Transformations.switchMap(gamesDataSourceFactory2.getGamesDataSource2LiveData()) {
            it.getResponseState()
        }
    }

    fun refresh() {
        Timber.d("refresh")
        gamesPagedListLiveData.value?.dataSource?.invalidate()
    }
}