package com.alacritystudios.vortex.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.vortex.data.domain.GamesModel
import com.alacritystudios.vortex.data.domain.GamesModelV5

import com.alacritystudios.vortex.databinding.RvItemGamesBinding
import com.alacritystudios.vortex.ui.adapter.GamesAdapter

class GamesViewHolder : RecyclerView.ViewHolder {

    var rvBinding: RvItemGamesBinding
    var gamesAdapterOnClickListener: GamesAdapter.GamesAdapterOnClickListener

    constructor(
        rvItemGamesBinding: RvItemGamesBinding,
        gamesAdapterOnClickListener: GamesAdapter.GamesAdapterOnClickListener) : super(rvItemGamesBinding.root) {
        this.rvBinding = rvItemGamesBinding
        this.gamesAdapterOnClickListener = gamesAdapterOnClickListener
    }

    fun bindViewToData(data: GamesModelV5.Top?): Unit {
        this.rvBinding.setTop(data)
        this.rvBinding.setOnItemClickListener(gamesAdapterOnClickListener)
    }
}