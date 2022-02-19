package com.joellui.ryuu.api

import com.joellui.ryuu.model.Anime
import com.joellui.ryuu.model.AnimeList
import com.joellui.ryuu.model.RandomList
import com.joellui.ryuu.utils.Constants.Companion.ANIAPI_VERSION
import com.joellui.ryuu.utils.Constants.Companion.NSFW
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AniApi {
    @GET("$ANIAPI_VERSION/anime/{id}")
    suspend fun getAnime(
        @Path("id") id: Int
    ): Response<Anime>

    @GET("$ANIAPI_VERSION/random/anime/{count}/$NSFW")
    suspend fun randomAnime(
        @Path("count") count: Int,
    ): Response<RandomList>

    @GET("$ANIAPI_VERSION/anime")
    suspend fun getSearchAnime(
        @Query("title") title: String? = null,
        @Query("anilist_id") AniList_id: Int? = null,
        @Query("mal_id") MAL_id: Int? = null,
        @Query("tmdb_id") TMDB_id: Int? = null,
        @Query("formats") Formats: String? = null,
        @Query("status") Status: String? = null,
        @Query("year") Year: Int? = null,
        @Query("season") Season: Int? = null,
        @Query("genres") Genres: String? = null,
        @Query("nsfw") nsfw: Boolean = NSFW.toBoolean(),
        @Query("with_episodes") With_episodes: Boolean? = false,
        @Query("page") page: Int? = null
        ): Response<AnimeList>
}