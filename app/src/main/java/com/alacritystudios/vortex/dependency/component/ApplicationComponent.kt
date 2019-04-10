package com.alacritystudios.vortex.dependency.component

import com.alacritystudios.vortex.application.VortexApplication
import com.alacritystudios.vortex.dependency.module.*
import com.alacritystudios.vortex.dependency.scope.VortexScope

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@VortexScope
@Component(modules = [AndroidSupportInjectionModule::class, AndroidInjectionModule::class, AppModule::class, ActivityModule::class, FragmentModule::class, ViewModelModule::class, RepositoryModule::class, NetworkModule::class, TwitchApiModule::class])
interface ApplicationComponent {

    fun inject(application: VortexApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun vortexApplication(application: VortexApplication): Builder

        fun build(): ApplicationComponent
    }
}
