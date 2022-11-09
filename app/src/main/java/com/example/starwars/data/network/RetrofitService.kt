package com.example.starwars.data.network

import com.example.starwars.data.entity.CharactersDto
import com.example.starwars.data.entity.FilmDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RetrofitService {
    @GET("people")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("search") searchPhrase: String,
    ): CharactersDto

    @GET
    suspend fun getFilm(
        @Url url: String,
    ): FilmDto
}
