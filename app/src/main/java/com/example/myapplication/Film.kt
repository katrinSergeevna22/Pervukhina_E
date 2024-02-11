package com.example.myapplication

data class Film(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: String,
    val filmLength: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val rating: String,
    val ratingVoteCount: String,
    val posterUrl: String,
    val posterUrlPreview: String,
)

data class Country(
    private val country: String
)

data class Genre(
    val genre: String
)

//            "filmId": 1115471,
//            "nameRu": "Мастер и Маргарита",
//            "nameEn": null,
//            "year": "2023",
//            "filmLength": "02:37",
//            "countries": [
//                {
//                    "country": "Россия"
//                }
//            ],
//            "genres": [
//                {
//                    "genre": "драма"
//                },
//                {
//                    "genre": "фэнтези"
//                }
//            ],
//            "rating": "98.0%",
//            "ratingVoteCount": 0,
//            "posterUrl": "https://kinopoiskapiunofficial.tech/images/posters/kp/1115471.jpg",
//            "posterUrlPreview": "https://kinopoiskapiunofficial.tech/images/posters/kp_small/1115471.jpg",
//            "ratingChange": null,
//            "isRatingUp": null,
//            "isAfisha": 0