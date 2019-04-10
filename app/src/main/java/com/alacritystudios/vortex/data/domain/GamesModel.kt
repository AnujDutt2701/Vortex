package com.alacritystudios.vortex.data.domain

data class GamesModel(
    val data: List<Data>,
    val pagination: Pagination
) {

    data class Data(
        var box_art_url: String,
        val id: String,
        val name: String
    )
}