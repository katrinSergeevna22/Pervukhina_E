package com.example.myapplication

object FavoriteListManager {
    private val favoriteList = ArrayList<Int>()

    fun getFavoriteList(): ArrayList<Int> {
        return favoriteList
    }

    fun addFilmToFavorites(filmId: Int) {
        if (!favoriteList.contains(filmId)) {
            favoriteList.add(filmId)
        }
    }

    fun removeFilmFromFavorites(filmId: Int) {
        favoriteList.remove(filmId)
    }
}
