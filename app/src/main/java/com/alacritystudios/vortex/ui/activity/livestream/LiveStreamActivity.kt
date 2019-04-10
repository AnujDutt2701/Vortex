package com.alacritystudios.vortex.ui.activity.livestream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alacritystudios.vortex.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alacritystudios.vortex.data.domain.StreamerToken
import com.alacritystudios.vortex.databinding.ActivityLiveStreamBinding
import com.alacritystudios.vortex.rest.adapter.ApiResponse
import com.alacritystudios.vortex.ui.fragment.games.GamesViewModel
import com.google.android.exoplayer2.ui.PlayerControlView
import timber.log.Timber
import javax.inject.Inject


class LiveStreamActivity : AppCompatActivity(), PlayerControlView.VisibilityListener {

    var url: String = "https://api.twitch.tv/kraken/streams/ninja"

    private val STREAMER_NAME: String = "STREAMER_NAME"

    private lateinit var activityLiveStreamBinding: ActivityLiveStreamBinding

    private lateinit var streamerName: String

    private lateinit var mViewModel: LiveStreamActivityViewModel

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LiveStreamActivityViewModel::class.java)
        activityLiveStreamBinding = DataBindingUtil.setContentView(this, R.layout.activity_live_stream, null)
        streamerName = getIntent().getStringExtra(STREAMER_NAME)
        setupViewModel()
        fetchStreamerToken()
    }

    private fun setupViewModel() {
        mViewModel.fetchStreamerToken().observe(this, Observer {
            
        })
    }

    private fun fetchStreamerToken() {
        mViewModel.refreshStreamerToken(streamerName)
    }

//    fun setupPlayer() {
//        mediaSource = ExtractorMediaSource(
//            Uri.parse(url),
//            dataSourceFactory,
//            extractorsFactory,
//            null, null
//        )
//        player.prepare(mediaSource)
//
//        activityLiveStreamBinding.playerView.setControllerVisibilityListener(this)
//        activityLiveStreamBinding.playerView.setErrorMessageProvider(PlayerErrorMessageProvider())
//    }
//
//    private fun buildMediaSource(uri: Uri, @Nullable overrideExtension: String): MediaSource {
//        @C.ContentType val type = Util.inferContentType(uri, overrideExtension)
//        when (type) {
//            C.TYPE_DASH -> return DashMediaSource.Factory(dataSourceFactory)
//                .setManifestParser(
//                    FilteringDashManifestParser(DashManifestParser(), getOfflineStreamKeys(uri))
//                )
//                .createMediaSource(uri)
//            C.TYPE_SS -> return SsMediaSource.Factory(dataSourceFactory)
//                .setManifestParser(
//                    FilteringSsManifestParser(SsManifestParser(), getOfflineStreamKeys(uri))
//                )
//                .createMediaSource(uri)
//            C.TYPE_HLS -> return HlsMediaSource.Factory(dataSourceFactory)
//                .setPlaylistParserFactory(
//                    DefaultHls
//                    DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(uri))
//                )
//                .createMediaSource(uri)
//            C.TYPE_OTHER -> return ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
//            else -> {
//                throw IllegalStateException("Unsupported type: $type")
//            }
//        }
//    }


    override fun onVisibilityChange(visibility: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
