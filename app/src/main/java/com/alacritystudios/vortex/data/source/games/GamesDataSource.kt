package com.alacritystudios.vortex.data.source.games

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.alacritystudios.vortex.data.domain.GamesModel
import com.alacritystudios.vortex.data.domain.GamesModelV5
import com.alacritystudios.vortex.rest.retrofit.service.TwitchApiV5Service
import com.alacritystudios.vortex.util.ConstantsUtil
import com.alacritystudios.vortex.util.HttpResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class GamesDataSource : PageKeyedDataSource<String, GamesModelV5.Top> {

    internal var twitchApiV5Service: TwitchApiV5Service
    internal var responseState: MutableLiveData<HttpResponseUtil>

    @Inject
    constructor(newTwitchApiService: TwitchApiV5Service) : super() {
        this.twitchApiV5Service = newTwitchApiService
        this.responseState = MutableLiveData()
    }

    fun getResponseState(): MutableLiveData<HttpResponseUtil> {
        return responseState
    }


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, GamesModelV5.Top>
    ) {
        Timber.d("loadInitial")
        val httpResponseUtil: HttpResponseUtil =
            HttpResponseUtil.loading(null, HttpResponseUtil.NetworkState.LOADING)
        responseState.postValue(httpResponseUtil)

        twitchApiV5Service.getTopGames(ConstantsUtil.TWITCH_API_CLIENT_ID, params.requestedLoadSize.toString(), null)
            .enqueue(object : Callback<GamesModelV5> {
                override fun onResponse(call: Call<GamesModelV5>, response: Response<GamesModelV5>) {
                    Timber.d("onResponse")
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Timber.d("response.body() != null")
                            val gamesModelV5: GamesModelV5 = response.body() as GamesModelV5
                            callback.onResult(
                                gamesModelV5.top,
                                null,
                                gamesModelV5._links.next.substring(gamesModelV5._links.next.indexOf("offset=") + 7)
                            )
                            val httpResponseUtil: HttpResponseUtil =
                                HttpResponseUtil.successfullyLoaded(HttpResponseUtil.NetworkState.LOADED)
                            responseState.postValue(httpResponseUtil)
                        } else {
                            // Unsuccessful call. Respond with failure message.
                            Timber.w("response.body() == null")
                            // TODO : set failure message.
                            val httpResponseUtil: HttpResponseUtil =
                                HttpResponseUtil.error("FAILURE MESSAGE", HttpResponseUtil.NetworkState.ERROR)
                            responseState.postValue(httpResponseUtil)

                        }
                    } else {
                        // Unsuccessful call. Respond with failure message.
                        Timber.w("response.isNotSuccessful")
                        // TODO : set failure message.
                        val httpResponseUtil: HttpResponseUtil =
                            HttpResponseUtil.error("FAILURE MESSAGE", HttpResponseUtil.NetworkState.ERROR)
                        responseState.postValue(httpResponseUtil)
                    }
                }

                override fun onFailure(call: Call<GamesModelV5>, t: Throwable) {
                    Timber.d("onFailure")
                    // TODO : set failure message.
                    val httpResponseUtil: HttpResponseUtil =
                        HttpResponseUtil.error("FAILURE MESSAGE", HttpResponseUtil.NetworkState.ERROR)
                    responseState.postValue(httpResponseUtil)
                }
            })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, GamesModelV5.Top>) {
        twitchApiV5Service.getTopGames(ConstantsUtil.TWITCH_API_CLIENT_ID, params.requestedLoadSize.toString(), params.key)
            .enqueue(object : Callback<GamesModelV5> {
                override fun onResponse(call: Call<GamesModelV5>, response: Response<GamesModelV5>) {
                    Timber.d("onResponse")
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Timber.d("response.body() != null")
                            val gamesModelV5: GamesModelV5 = response.body() as GamesModelV5
                            callback.onResult(gamesModelV5.top, gamesModelV5._links.next.substring(gamesModelV5._links.next.indexOf("offset=") + 7))
                        } else {
                            // Unsuccessful call. Respond with failure message.
                            Timber.w("response.body() == null")
                            // TODO : set failure message.
                            val httpResponseUtil: HttpResponseUtil =
                                HttpResponseUtil.error("FAILURE MESSAGE", HttpResponseUtil.NetworkState.LOADED)
                            responseState.postValue(httpResponseUtil)
                        }
                    } else {
                        // Unsuccessful call. Respond with failure message.
                        Timber.w("response.isNotSuccessful")
                        // TODO : set failure message.
                        val httpResponseUtil: HttpResponseUtil =
                            HttpResponseUtil.error("FAILURE MESSAGE", HttpResponseUtil.NetworkState.LOADED)
                        responseState.postValue(httpResponseUtil)
                    }
                }

                override fun onFailure(call: Call<GamesModelV5>, t: Throwable) {
                    Timber.d("onFailure")
                    // TODO : set failure message.
                    val httpResponseUtil: HttpResponseUtil =
                        HttpResponseUtil.error("FAILURE MESSAGE", HttpResponseUtil.NetworkState.LOADED)
                    responseState.postValue(httpResponseUtil)
                }
            })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, GamesModelV5.Top>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}