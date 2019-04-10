package com.alacritystudios.vortex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.alacritystudios.vortex.R
import com.alacritystudios.vortex.data.domain.GamesModel
import com.alacritystudios.vortex.data.domain.GamesModelV5
import com.alacritystudios.vortex.databinding.RvItemGamesBinding
import com.alacritystudios.vortex.ui.binding.BindingComponent
import com.alacritystudios.vortex.ui.binding.CommonBindingUtils
import com.alacritystudios.vortex.ui.viewholder.GamesViewHolder
import com.alacritystudios.vortex.util.ConstantsUtil


class GamesAdapter : PagedListAdapter<GamesModelV5.Top, GamesViewHolder> {

    var bindingComponent: BindingComponent
    var gamesAdapterOnClickListener: GamesAdapterOnClickListener

    constructor(
        bindingComponent: BindingComponent,
        gamesAdapterOnClickListener: GamesAdapterOnClickListener,
        diffCallback: DiffUtil.ItemCallback<GamesModelV5.Top>
    ) : super(
        diffCallback
    ) {
        this.bindingComponent = bindingComponent
        this.gamesAdapterOnClickListener = gamesAdapterOnClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val binding = DataBindingUtil.inflate<RvItemGamesBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_games,
            parent,
            false,
            bindingComponent
        )
        return GamesViewHolder(binding, gamesAdapterOnClickListener)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        holder.bindViewToData(getItem(position))
        holder.rvBinding.ivGameArt.clipToOutline = true;
    }


    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<GamesModelV5.Top>() {
            override fun areContentsTheSame(oldItem: GamesModelV5.Top, newItem: GamesModelV5.Top): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: GamesModelV5.Top, newItem: GamesModelV5.Top): Boolean =
                oldItem.game._id == newItem.game._id

            override fun getChangePayload(oldItem: GamesModelV5.Top, newItem: GamesModelV5.Top): Any? {
                return if (sameExceptScore(oldItem, newItem)) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }
        }

        private fun sameExceptScore(oldItem: GamesModelV5.Top, newItem: GamesModelV5.Top): Boolean {
            return ((oldItem.channels == newItem.channels) && (oldItem.viewers == newItem.viewers))
        }
    }

    interface GamesAdapterOnClickListener {

        fun onItemClick(game: GamesModelV5.Game)
    }
}