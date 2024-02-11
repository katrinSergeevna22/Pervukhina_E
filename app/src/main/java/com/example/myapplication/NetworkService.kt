package com.example.myapplication

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

object NetworkService {
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(CacheInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(KinopoiskApiService::class.java)

    suspend fun getFilms(): Result<List<Film>> {
        val filmsResponse = service.getTopFilm("TOP_100_POPULAR_FILMS", API_KEY)
        return when (filmsResponse.isSuccessful) {
            true -> Result.success(filmsResponse.body()?.films ?: emptyList())
            false -> Result.failure(IllegalStateException())
        }
    }
    suspend fun getFilmsDetails(filmId: Int): Result<List<Film>> {
        val filmsResponse = service.getTopFilm("TOP_100_POPULAR_FILMS", API_KEY)
        val detailsForFilmsResponse = service.getFilmDetails(filmId, API_KEY)
        return when (filmsResponse.isSuccessful) {
            true -> Result.success(filmsResponse.body()?.films ?: emptyList())
            false -> Result.failure(IllegalStateException())
        }
    }

}