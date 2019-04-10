package com.alacritystudios.vortex.util

import androidx.paging.PageKeyedDataSource
import com.alacritystudios.vortex.data.domain.GamesModel
import retrofit2.Response

class HttpResponseUtil {

    var message: String? = null
    var networkLoad: NetworkState? = null
    var initialLoad: NetworkState? = null
    var responseState: ResponseState? = null

    enum class ResponseState {
        RESPONSE_SUCCESSFUL,
        RESPONSE_UNSUCCESSFUL
    }

    enum class NetworkState {
        LOADING,
        LOADED,
        ERROR
    }

    companion object {

        fun successfullyLoaded(initialLoad: NetworkState): HttpResponseUtil {

            val httpResponseUtil = HttpResponseUtil()
            httpResponseUtil.initialLoad = initialLoad
            httpResponseUtil.networkLoad = NetworkState.LOADED
            httpResponseUtil.responseState = ResponseState.RESPONSE_SUCCESSFUL
            return httpResponseUtil
        }

        fun loading(message: String?, initialLoad: NetworkState): HttpResponseUtil {

            val httpResponseUtil = HttpResponseUtil()
            httpResponseUtil.message = message
            httpResponseUtil.initialLoad = initialLoad
            httpResponseUtil.networkLoad = NetworkState.LOADING
            httpResponseUtil.responseState = null
            return httpResponseUtil
        }

        fun error(message: String?, initialLoad: NetworkState): HttpResponseUtil {

            val httpResponseUtil = HttpResponseUtil()
            httpResponseUtil.message = message
            httpResponseUtil.initialLoad = initialLoad
            httpResponseUtil.networkLoad = NetworkState.ERROR
            httpResponseUtil.responseState = ResponseState.RESPONSE_UNSUCCESSFUL
            return httpResponseUtil
        }
    }
}