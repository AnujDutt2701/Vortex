package com.alacritystudios.vortex.ui.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.alacritystudios.vortex.R
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class CommonBindingUtils {

    companion object {

        val decimalFormatter = DecimalFormat("#,###,###")

        @BindingAdapter("imageUrl")
        fun setImageUrl(view: ImageView, url: String) {
            Glide.with(view).load(url).into(view)
        }

        @BindingAdapter("boxImageUrl")
        fun setBoxImageUrl(view: ImageView, url: String) {
            Glide.with(view).load(url.replace("{width}", "250").replace("{height}", "300")).into(view)
        }

        @BindingAdapter("formattedNumber")
        fun setBoxImageUrl(view: TextView, number: Int) {
            view.text = decimalFormatter.format(number)
        }

        @BindingAdapter("formattedViewers")
        fun setFormattedViewers(view: TextView, number: Int) {
            view.text = view.context.getString(R.string.games_viewers, decimalFormatter.format(number))
        }

        @BindingAdapter("formattedChannels")
        fun setFormattedChannels(view: TextView, number: Int) {
            view.text = view.context.getString(R.string.games_channels, decimalFormatter.format(number))
        }
    }

}