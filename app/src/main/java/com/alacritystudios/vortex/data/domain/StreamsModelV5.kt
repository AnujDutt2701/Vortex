package com.alacritystudios.vortex.data.domain

data class StreamsModelV5(
    val _links: Links,
    val _total: Int,
    val streams: List<Stream>
) {

    data class Stream(
        val _id: Long,
        val average_fps: Float,
        val channel: Channel,
        val created_at: String,
        val delay: Int,
        val game: String,
        val is_playlist: Boolean,
        val preview: Preview,
        val stream_type: String,
        val video_height: Int,
        val viewers: Int
    )

    data class Preview(
        val large: String,
        val medium: String,
        val small: String,
        val template: String
    )

    data class Channel(
        val _id: Int,
        val background: Any,
        val banner: Any,
        val broadcaster_language: String,
        val broadcaster_software: String,
        val created_at: String,
        val delay: Any,
        val display_name: String,
        val followers: Int,
        val game: String,
        val language: String,
        val logo: String,
        val mature: Boolean,
        val name: String,
        val partner: Boolean,
        val profile_banner: String,
        val profile_banner_background_color: String,
        val status: String,
        val updated_at: String,
        val url: String,
        val video_banner: String,
        val views: Int
    )
    data class Links(
        val next: String,
        val self: String
    )
}