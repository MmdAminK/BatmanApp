package com.app.batman.services

import com.app.batman.models.Film
import com.app.batman.models.MainPageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetFilmsWebServices {

    @GET(".")
    suspend fun getFilms(
        @Query("apikey") apiKey: String,
        @Query("s") s: String
    ): Response<MainPageDto?>

    @GET(".")
    suspend fun getFilmDetail(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): Response<Film?>
}