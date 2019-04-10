package com.alacritystudios.vortex.dependency.module

import android.content.Context
import androidx.databinding.DataBindingComponent
import com.alacritystudios.vortex.application.VortexApplication
import com.alacritystudios.vortex.ui.binding.BindingComponent
import com.alacritystudios.vortex.util.PreferenceUtil
import com.alacritystudios.vortex.util.ThemeUtil

import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun providesApplicationContext(): Context {
        return VortexApplication.getVortexApplicationInstance().getApplicationContext()
    }

    @Provides
    fun providesPreferenceUtil(context: Context): PreferenceUtil {
        return PreferenceUtil(context)
    }

    @Provides
    fun providesThemeUtil(preferenceUtil: PreferenceUtil): ThemeUtil {
        return ThemeUtil(preferenceUtil)
    }

    @Provides
    fun providesDataBindingComponent(): BindingComponent {
        return BindingComponent()
    }
}
