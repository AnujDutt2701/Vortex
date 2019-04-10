package com.alacritystudios.vortex.ui.viewholder

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.vortex.BR

class GenericViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(obj: Any) {
//        binding.setVariable(BR.obj, obj)
//        binding.executePendingBindings()
    }
}