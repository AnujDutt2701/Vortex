package com.alacritystudios.vortex.data.source.streams

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.alacritystudios.vortex.data.domain.StreamsModelV5
import com.alacritystudios.vortex.rest.retrofit.service.TwitchApiV5Service
import com.alacritystudios.vortex.util.ConstantsUtil
import com.alacritystudios.vortex.util.HttpResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class StreamsDataSource : PageKeyedDataSource<String, StreamsModelV5.Stream> {

    internal var twitchApiV5Service: TwitchApiV5Service
    internal var game: String
    internal var responseState: MutableLiveData<HttpResponseUtil>

    @Inject
    constructor(twitchApiV5Service: TwitchApiV5Service, game: String) : super() {
        this.twitchApiV5Service = twitchApiV5Service
        this.game = game
        this.responseState = MutableLiveData()
    }

    fun getResponseState(): MutableLiveData<HttpResponseUtil> {
        return responseState
    }


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, StreamsModelV5.Stream>
    ) {
        Timber.d("loadInitial")
        val httpResponseUtil: HttpResponseUtil =
            HttpResponseUtil.loading(null, HttpResponseUtil.NetworkState.LOADING)
        responseState.postValue(httpResponseUtil)

        twitchApiV5Service.getTopStreams(
            ConstantsUtil.TWITCH_API_CLIENT_ID,
            game,
            params.requestedLoadSize.toString(),
            null
        )
            .enqueue(object : Callback<StreamsModelV5> {
                override fun onResponse(call: Call<StreamsModelV5>, response: Response<StreamsModelV5>) {
                    Timber.d("onResponse")
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Timber.d("response.body() != null")
                            val streamsModelV5: StreamsModelV5 = response.body() as StreamsModelV5
                            callback.onResult(
                                streamsModelV5.streams,
                                null,
                                streamsModelV5._links.next.substring(streamsModelV5._links.next.indexOf("offset=") + 7, streamsModelV5._links.next.indexOf("&stream_type"))
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

                override fun onFailure(call: Call<StreamsModelV5>, t: Throwable) {
                    Timber.d("onFailure")
                    // TODO : set failure message.
                    val httpResponseUtil: HttpResponseUtil =
                        HttpResponseUtil.error("FAILURE MESSAGE", HttpResponseUtil.NetworkState.ERROR)
                    responseState.postValue(httpResponseUtil)
                }
            })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, StreamsModelV5.Stream>) {
        twitchApiV5Service.getTopStreams(
            ConstantsUtil.TWITCH_API_CLIENT_ID, game,
            params.requestedLoadSize.toString(),
            params.key
        )
            .enqueue(object : Callback<StreamsModelV5> {
                override fun onResponse(call: Call<StreamsModelV5>, response: Response<StreamsModelV5>) {
                    Timber.d("onResponse")
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            Timber.d("response.body() != null")
                            val streamsModelV5: StreamsModelV5 = response.body() as StreamsModelV5
                            callback.onResult(
                                streamsModelV5.streams,
                                streamsModelV5._links.next.substring(streamsModelV5._links.next.indexOf("offset=") + 7, streamsModelV5._links.next.indexOf("&stream_type"))
                            )
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

                override fun onFailure(call: Call<StreamsModelV5>, t: Throwable) {
                    Timber.d("onFailure")
                    // TODO : set failure message.
                    val httpResponseUtil: HttpResponseUtil =
                        HttpResponseUtil.error("FAILURE MESSAGE", HttpResponseUtil.NetworkState.LOADED)
                    responseState.postValue(httpResponseUtil)
                }
            })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, StreamsModelV5.Stream>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}