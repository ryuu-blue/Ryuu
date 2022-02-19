package com.joellui.ryuu.model

data class Anime(
    val status_code: Int,
    val message: String,
    val data: AnimeDetails,
    val version: String
)
