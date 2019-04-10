package com.alacritystudios.vortex.data.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.alacritystudios.vortex.data.domain.GamesModel
import com.alacritystudios.vortex.rest.retrofit.service.NewTwitchApiService
import com.alacritystudios.vortex.util.HttpResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

class GamesDataSource : PageKeyedDataSource<String, GamesModel.Data> {

    internal var newTwitchApiService: NewTwitchApiService
    internal var initialLoad: MutableLiveData<HttpResponseUtil.NetworkState>
    internal var networkLoad: MutableLiveData<HttpResponseUtil.NetworkState>
    internal var retryExecutor: Executor

    @Inject
    constructor(newTwitchApiService: NewTwitchApiService, retryExecutor: Executor) : super() {
        this.newTwitchApiService = newTwitchApiService
        this.retryExecutor = retryExecutor
        this.initialLoad = MutableLiveData()
        this.networkLoad = MutableLiveData()
    }

    fun getNetworkLoad(): MutableLiveData<*> {
        return networkLoad
    }

    fun getInitialLoad(): MutableLiveData<*> {
        return initialLoad
    }

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, GamesModel.Data>
    ) {
        initialLoad.postValue(HttpResponseUtil.NetworkState.LOADING)
        networkLoad.postValue(HttpResponseUtil.NetworkState.LOADING)

        newTwitchApiService.getTopGames("100", "", "").enqueue(object : Callback<GamesModel> {
            override fun onResponse(call: Call<GamesModel>, response: Response<GamesModel>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val gamesModel: GamesModel = response.body() as GamesModel
                        callback.onResult(gamesModel.data, null, gamesModel.pagination.cursor)
                        initialLoad.postValue(HttpResponseUtil.NetworkState.LOADED)
                        networkLoad.postValue(HttpResponseUtil.NetworkState.LOADED)
                    } else {

                    }
                } else {
                    // Unsuccessful call. Respond with failure message.
                    retry = {
                        loadInitial(params, callback)
                    }
                }


            }

            override fun onFailure(call: Call<GamesModel>, t: Throwable) {
            }
        })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, GamesModel.Data>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, GamesModel.Data>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}