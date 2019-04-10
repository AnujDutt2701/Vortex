package com.alacritystudios.vortex.dependency.module


import com.alacritystudios.vortex.ui.VortexViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alacritystudios.vortex.dependency.key.ViewModelKey
import com.alacritystudios.vortex.ui.activity.livestream.LiveStreamActivityViewModel
import com.alacritystudios.vortex.ui.fragment.browse.BrowseViewModel
import com.alacritystudios.vortex.ui.fragment.following.FollowingViewModel
import com.alacritystudios.vortex.ui.fragment.game.GameViewModel
import com.alacritystudios.vortex.ui.fragment.games.GamesViewModel
import com.alacritystudios.vortex.ui.fragment.home.HomeViewModel
import com.alacritystudios.vortex.ui.fragment.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: VortexViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(GamesViewModel::class)
    abstract fun contributesCurrenciesViewModel(gamesViewModel: GamesViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(GameViewModel::class)
    abstract fun contributesCurrencyViewModel(gameViewModel: GameViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(BrowseViewModel::class)
    abstract fun contributesBrowseViewModel(browseViewModel: BrowseViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(FollowingViewModel::class)
    abstract fun contributesFollowingViewModel(followingViewModel: FollowingViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(HomeViewModel::class)
    abstract fun contributesHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(LiveStreamActivityViewModel::class)
    abstract fun contributesLiveStreamActivityViewModel(liveStreamActivityViewModel: LiveStreamActivityViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(SearchViewModel::class)
    abstract fun contributesSearchViewModel(searchViewModel: SearchViewModel): ViewModel
}
