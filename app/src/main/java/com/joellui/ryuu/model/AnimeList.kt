package com.joellui.ryuu.model

data class AnimeList(
    val status_code : Int,
    val message: String,
    val data: AnimeListData,
    val version: String
)

data class AnimeListData (
    val current_page : Int?,
    val count : Int?,
    val documents : List<AnimeDetails>,
    val last_page: Int?
        )

data class RandomList(
    val status_code : Int,
    val message: String,
    val data: List<AnimeDetails>,
    val version: String
)
