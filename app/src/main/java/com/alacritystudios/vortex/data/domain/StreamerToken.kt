package com.alacritystudios.vortex.data.domain

data class StreamerToken(
    val expires_at: String,
    val mobile_restricted: Boolean,
    val sig: String,
    val token: String
)