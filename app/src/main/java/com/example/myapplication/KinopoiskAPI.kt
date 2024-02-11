package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

data class TopFilmsResponse(
    val pagesCount: Int,
    val films: List<Film>
)

data class FilmDescriptionResponse(val film: Film)


interface KinopoiskApiService {
    @GET("/api/v2.2/films/top")
    suspend fun getTopFilm(
        @Query("type") type: String,
        @Header("X-API-KEY") apiKey: String,
    ): Response<TopFilmsResponse>

    @GET("/api/v2.2/films/top/{movieId}")
    suspend fun getFilmDetails(
        @Path("movieId") filmId: Int,
        @Header("X-API-KEY") apiKey: String
    ): Response<FilmDescriptionResponse>
}



