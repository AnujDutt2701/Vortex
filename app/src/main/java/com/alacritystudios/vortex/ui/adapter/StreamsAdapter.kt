package com.alacritystudios.vortex.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.alacritystudios.vortex.R
import com.alacritystudios.vortex.data.domain.StreamsModelV5
import com.alacritystudios.vortex.databinding.RvItemStreamsBinding
import com.alacritystudios.vortex.ui.binding.BindingComponent
import com.alacritystudios.vortex.ui.viewholder.StreamsViewHolder

class StreamsAdapter : PagedListAdapter<StreamsModelV5.Stream, StreamsViewHolder> {

    var bindingComponent: BindingComponent
    var streamsAdapterOnClickListener: StreamsAdapterOnClickListener

    constructor(
        bindingComponent: BindingComponent,
        streamsAdapterOnClickListener: StreamsAdapterOnClickListener,
        diffCallback: DiffUtil.ItemCallback<StreamsModelV5.Stream>
    ) : super(
        diffCallback
    ) {
        this.bindingComponent = bindingComponent
        this.streamsAdapterOnClickListener = streamsAdapterOnClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamsViewHolder {
        val binding = DataBindingUtil.inflate<RvItemStreamsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_streams,
            parent,
            false,
            bindingComponent
        )
        return StreamsViewHolder(binding, streamsAdapterOnClickListener)
    }

    override fun onBindViewHolder(holder: StreamsViewHolder, position: Int) {
        holder.bindViewToData(getItem(position))
    }


    companion object {
        private val PAYLOAD_SCORE = Any()
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<StreamsModelV5.Stream>() {
            override fun areContentsTheSame(oldItem: StreamsModelV5.Stream, newItem: StreamsModelV5.Stream): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: StreamsModelV5.Stream, newItem: StreamsModelV5.Stream): Boolean =
                oldItem.channel == newItem.channel

            override fun getChangePayload(oldItem: StreamsModelV5.Stream, newItem: StreamsModelV5.Stream): Any? {
                return if (sameExceptScore(oldItem, newItem)) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }
        }

        private fun sameExceptScore(oldItem: StreamsModelV5.Stream, newItem: StreamsModelV5.Stream): Boolean {
            return ((oldItem.channel == newItem.channel) && (oldItem.viewers == newItem.viewers))
        }
    }

    interface StreamsAdapterOnClickListener {

        fun onItemClick(stream: StreamsModelV5.Stream)
    }
}