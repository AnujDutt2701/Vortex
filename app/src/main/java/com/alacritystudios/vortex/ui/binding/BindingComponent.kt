package com.alacritystudios.vortex.ui.binding

import androidx.databinding.DataBindingComponent

class BindingComponent : DataBindingComponent {

    override fun getCompanion(): CommonBindingUtils.Companion {
        return CommonBindingUtils
    }
}