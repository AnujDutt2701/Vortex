package com.alacritystudios.vortex.ui.activity.livestream

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.alacritystudios.vortex.data.domain.StreamerToken
import com.alacritystudios.vortex.data.repository.TwitchRepository
import com.alacritystudios.vortex.rest.adapter.ApiResponse
import javax.inject.Inject

class LiveStreamActivityViewModel : ViewModel {

    private var twitchRepository: TwitchRepository
    private var streamerNameLiveData: MutableLiveData<String>
    private lateinit var streamerTokenLiveData: MediatorLiveData<ApiResponse<StreamerToken>>

    @Inject
    constructor(twitchRepository: TwitchRepository) : super() {
        this.twitchRepository = twitchRepository
        this.streamerNameLiveData = MutableLiveData();
        streamerTokenLiveData.addSource(streamerNameLiveData, Observer {
            twitchRepository.getStreamerToken(it)
        })
    }

    public fun fetchStreamerToken(): MediatorLiveData<ApiResponse<StreamerToken>> {
        return streamerTokenLiveData
    }

    public fun refreshStreamerToken(streamerName: String) {
        streamerNameLiveData.setValue(streamerName)
    }

}