package com.joellui.ryuu.repository

import com.joellui.ryuu.api.RetrofitInstance
import com.joellui.ryuu.model.Anime
import com.joellui.ryuu.model.AnimeList
import com.joellui.ryuu.model.RandomList
import retrofit2.Response

class Repository {

    suspend fun getAnime(id: Int): Response<Anime> {
        return RetrofitInstance.animeApi.getAnime(id)
    }

    suspend fun randomAnimeList(): Response<RandomList>{
        return RetrofitInstance.animeApi.randomAnime(49)
    }

    suspend fun getSearchAnime(
        title: String?,
        format: String?,
        status:String?,
        year: Int?,
        season: Int?,
        genres: String?,
        page: Int?

    ): Response<AnimeList>{
        return RetrofitInstance.animeApi.getSearchAnime(
            title = title,
            Formats = format,
            Status = status,
            Year = year,
            Season = season,
            Genres = genres,
            page = page
        )
    }
}