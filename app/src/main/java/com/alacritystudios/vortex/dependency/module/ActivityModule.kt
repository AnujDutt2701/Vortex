package com.alacritystudios.vortex.dependency.module

import com.alacritystudios.vortex.ui.activity.MainActivity
import com.alacritystudios.vortex.ui.activity.SettingsActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
//
//    @ContributesAndroidInjector
//    internal abstract fun settingsActivity(): SettingsActivity
}