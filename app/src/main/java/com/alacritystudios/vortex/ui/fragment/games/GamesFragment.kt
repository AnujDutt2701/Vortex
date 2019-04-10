package com.alacritystudios.vortex.ui.fragment.games

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.alacritystudios.vortex.R

import javax.inject.Inject

import com.alacritystudios.vortex.databinding.GamesFragmentBinding
import com.alacritystudios.vortex.ui.adapter.GamesAdapter
import com.alacritystudios.vortex.ui.binding.BindingComponent
import com.alacritystudios.vortex.util.UiUtil
import timber.log.Timber
import androidx.recyclerview.widget.DividerItemDecoration
import com.alacritystudios.vortex.data.domain.GamesModelV5
import com.alacritystudios.vortex.ui.fragment.game.GameFragment


class GamesFragment : Fragment(), GamesAdapter.GamesAdapterOnClickListener {

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    @Inject
    internal lateinit var mBindingComponent: BindingComponent;

    private var mViewModel: GamesViewModel? = null

    internal lateinit var gamesFragmentBinding: GamesFragmentBinding

    private lateinit var gamesAdapter: GamesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.d("onCreateView")
        gamesFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.games_fragment, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = getText(R.string.nav_browse_fragment)
        setupRecyclerView()
        return gamesFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Timber.d("onActivityCreated")
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GamesViewModel::class.java)
        setLiveDataListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    private fun setupRecyclerView() {
        Timber.d("setupRecyclerView")
        gamesAdapter = GamesAdapter(mBindingComponent, this, GamesAdapter.POST_COMPARATOR)
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        gamesFragmentBinding.rvGames.setAdapter(gamesAdapter)
        gamesFragmentBinding.rvGames.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(
            gamesFragmentBinding.rvGames.getContext(),
            linearLayoutManager.getOrientation()
        )
        gamesFragmentBinding.rvGames.addItemDecoration(dividerItemDecoration)
    }

    private fun setLiveDataListeners() {
        Timber.d("setLiveDataListeners")

        if (mViewModel != null) {
            gamesFragmentBinding.srlGames.setOnRefreshListener {
                (mViewModel as GamesViewModel).refresh()
            }
            (mViewModel as GamesViewModel).initiateFind()
            (mViewModel as GamesViewModel).gamesPagedListLiveData.observe(this, Observer {
                gamesAdapter.submitList(it)
            })

            (mViewModel as GamesViewModel).responseStateLiveData.observe(this, Observer {
                UiUtil.setLoadingBehaviour(
                    gamesFragmentBinding.srlGames, gamesFragmentBinding.rvGames
                    , gamesFragmentBinding.clGames, it
                )
            })
        }
    }

    override fun onItemClick(game: GamesModelV5.Game) {
        val navController = Navigation.findNavController(activity as Activity, R.id.fragment)
        var bundle = Bundle()
        bundle.putString("GAME_NAME", game.name)
        navController.navigate(R.id.gameFragment, bundle)
    }

    companion object {

        fun newInstance(): GamesFragment {
            return GamesFragment()
        }
    }
}
