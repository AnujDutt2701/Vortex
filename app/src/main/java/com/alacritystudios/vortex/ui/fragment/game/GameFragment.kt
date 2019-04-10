package com.alacritystudios.vortex.ui.fragment.game;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.alacritystudios.vortex.R;
import com.alacritystudios.vortex.data.domain.StreamsModelV5
import com.alacritystudios.vortex.databinding.GameFragmentBinding
import com.alacritystudios.vortex.ui.adapter.StreamsAdapter
import com.alacritystudios.vortex.ui.binding.BindingComponent
import com.alacritystudios.vortex.util.UiUtil
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber

import javax.inject.Inject;

class GameFragment : Fragment(), StreamsAdapter.StreamsAdapterOnClickListener {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var mBindingComponent: BindingComponent

    private var mViewModel: GameViewModel? = null

    internal lateinit var gameFragmentBinding: GameFragmentBinding

    private lateinit var streamsAdapter: StreamsAdapter

    val GAME_NAME: String = "GAME_NAME"

    lateinit var gameName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView")
        gameFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        gameName = arguments!!.getString(GAME_NAME)
        (activity as AppCompatActivity).supportActionBar?.title = gameName
        setupRecyclerView()
        return gameFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.d("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GameViewModel::class.java)
        setLiveDataListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    private fun setupRecyclerView() {
        Timber.d("setupRecyclerView")
        streamsAdapter = StreamsAdapter(mBindingComponent, this, StreamsAdapter.POST_COMPARATOR)
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        gameFragmentBinding.rvStreams.setAdapter(streamsAdapter)
        gameFragmentBinding.rvStreams.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(
            gameFragmentBinding.rvStreams.getContext(),
            linearLayoutManager.getOrientation()
        )
        gameFragmentBinding.rvStreams.addItemDecoration(dividerItemDecoration)
    }

    private fun setLiveDataListeners() {
        Timber.d("setLiveDataListeners")

        if (mViewModel != null) {
            gameFragmentBinding.srlStreams.setOnRefreshListener {
                (mViewModel as GameViewModel).refresh()
            }
            (mViewModel as GameViewModel).initiateFind(gameName)
            (mViewModel as GameViewModel).streamsLiveData.observe(this, Observer {
                streamsAdapter.submitList(it)
            })

            (mViewModel as GameViewModel).responseStateLiveData.observe(this, Observer {
                UiUtil.setLoadingBehaviour(
                    gameFragmentBinding.srlStreams, gameFragmentBinding.rvStreams
                    , gameFragmentBinding.clStreams, it
                )
            })
        }
    }

    override fun onItemClick(stream: StreamsModelV5.Stream) {

    }
}
