package com.alacritystudios.vortex.application

import android.app.Activity
import android.app.Application
import android.app.Service
import com.alacritystudios.vortex.dependency.component.ApplicationComponent
import com.alacritystudios.vortex.dependency.component.DaggerApplicationComponent
import dagger.android.*
import timber.log.Timber
import javax.inject.Inject

class VortexApplication : Application, HasActivityInjector, HasServiceInjector {

    @Inject
    internal lateinit var activityAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    internal lateinit var serviceAndroidInjector: DispatchingAndroidInjector<Service>

    private lateinit var applicationComponent: ApplicationComponent

    constructor()

    constructor(
        activityAndroidInjector: DispatchingAndroidInjector<Activity>,
        serviceAndroidInjector: DispatchingAndroidInjector<Service>
    ) : super() {
        this.activityAndroidInjector = activityAndroidInjector
        this.serviceAndroidInjector = serviceAndroidInjector
    }

    companion object {
        internal lateinit var vortexApplication: VortexApplication
        fun getVortexApplicationInstance(): VortexApplication = vortexApplication
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        vortexApplication = this;
        applicationComponent = DaggerApplicationComponent.builder().vortexApplication(this).build();
        applicationComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityAndroidInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return serviceAndroidInjector
    }
}