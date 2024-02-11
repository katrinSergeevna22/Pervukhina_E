package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.coroutineScope
import com.example.myapplication.adapter.FilmAdapter
import com.example.myapplication.databinding.ActivityFilmBinding
import com.example.myapplication.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmDescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        val filmId = this.intent.getIntExtra("filmId", -1)
        Log.d("MyLOG", filmId.toString())


        lifecycle.coroutineScope.launch {
            when (val film = NetworkService.getFilms().getOrNull()?.find{it.filmId == filmId}) {
                null -> {
                    val intent = Intent(this@FilmDescriptionActivity, ErrorActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    binding.tvTitileOfFilm.text = film.nameRu
                    val genres = parseGenres(film.genres.toString(), "genre").capitalize()
                    binding?.tvDesc?.text = genres + " (" + film.year + ")" + "\n" + "Страна производства: " + parseGenres(film.countries.toString(), "country")
                    Picasso.with(this@FilmDescriptionActivity)
                     .load(film.posterUrl)
                     .into(binding?.ivPosterFilm)
                    launch(Dispatchers.Main) {  }
                }
            }
        }




        binding.ibBack2.setOnClickListener(){
            val intent = Intent(this@FilmDescriptionActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun parseGenres(input: String, keyWord: String): String {
        val pattern = Regex(keyWord + "=([a-zA-Zа-яА-Я]+)")
        val matches = pattern.findAll(input)

        val genresList = mutableListOf<String>()

        for (match in matches) {
            genresList.add(match.groupValues[1])
        }
        return genresList[0]
    }


}
