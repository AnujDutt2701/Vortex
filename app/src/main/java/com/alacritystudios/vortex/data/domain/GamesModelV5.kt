package com.alacritystudios.vortex.data.domain


data class GamesModelV5(
    val _links: Links,
    val _total: Int,
    val top: List<Top>
) {

    data class Game(
        val _id: Int,
        val box: Box,
        val giantbomb_id: Int,
        val locale: String,
        val localized_name: String,
        val logo: Logo,
        val name: String,
        val popularity: Int
    )

    data class Box(
        val large: String,
        val medium: String,
        val small: String,
        val template: String
    )

    data class Links(
        val next: String,
        val self: String
    )

    data class Logo(
        val large: String,
        val medium: String,
        val small: String,
        val template: String
    )

    data class Top(
        val channels: Int,
        val game: Game,
        val viewers: Int
    )
}