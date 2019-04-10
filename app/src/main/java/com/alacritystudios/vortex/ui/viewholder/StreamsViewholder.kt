package com.alacritystudios.vortex.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.alacritystudios.vortex.data.domain.StreamsModelV5
import com.alacritystudios.vortex.databinding.RvItemStreamsBinding
import com.alacritystudios.vortex.ui.adapter.StreamsAdapter

class StreamsViewHolder : RecyclerView.ViewHolder {

    var rvBinding: RvItemStreamsBinding
    var streamsAdapterOnClickListener: StreamsAdapter.StreamsAdapterOnClickListener

    constructor(
        rvItemStreamsBinding: RvItemStreamsBinding,
        streamsAdapterOnClickListener: StreamsAdapter.StreamsAdapterOnClickListener) : super(rvItemStreamsBinding.root) {
        this.rvBinding = rvItemStreamsBinding
        this.streamsAdapterOnClickListener = streamsAdapterOnClickListener
    }

    fun bindViewToData(data: StreamsModelV5.Stream?): Unit {
        this.rvBinding.stream = data
        this.rvBinding.setOnItemClickListener(streamsAdapterOnClickListener)
    }
}