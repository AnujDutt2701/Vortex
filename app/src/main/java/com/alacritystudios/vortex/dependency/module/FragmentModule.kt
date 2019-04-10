package com.alacritystudios.vortex.dependency.module


import com.alacritystudios.vortex.ui.fragment.browse.BrowseFragment
import com.alacritystudios.vortex.ui.fragment.following.FollowingFragment
import com.alacritystudios.vortex.ui.fragment.games.GamesFragment
import com.alacritystudios.vortex.ui.fragment.game.GameFragment
import com.alacritystudios.vortex.ui.fragment.home.HomeFragment
import com.alacritystudios.vortex.ui.fragment.search.SearchFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun followingFragment(): FollowingFragment

    @ContributesAndroidInjector
    abstract fun gamesFragment(): GamesFragment

    @ContributesAndroidInjector
    abstract fun gameFragment(): GameFragment

    @ContributesAndroidInjector
    abstract fun browseFragment(): BrowseFragment

    @ContributesAndroidInjector
    abstract fun searchFragment(): SearchFragment
}
