package com.joellui.ryuu.model

data class AnimeDetails(
    val anilist_id : Int,
    val mal_id : Int?,
    val format : Int?,
    val status : Int,
    val titles : Tilte,
    val descriptions : Description,
    val start_date : String?,
    val end_date : String?,
    val season_period : Int,
    val season_year : Int?,
    val episodes_count : Int,
    val episode_duration : Int?,
    val trailer_url : String?,
    val cover_image : String,
    val cover_color : String?,
    val banner_image : String?,
    val sequel : Int?,
    val prequel : Int?,
    val genres : List<String>,
    val score : Int,
    val nsfw : Boolean,
    val hasCoverImage : Boolean,
    val id : Int
)

data class Tilte(
    val en : String?,
    val jp: String?,
    val it : String?
)

data class Description (
    val en: String,
    val it: String?
        )


