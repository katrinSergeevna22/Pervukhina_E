package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.FilmAdapter
import com.example.myapplication.databinding.ActivityFavoriteBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnPop.setOnClickListener{
            val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        var favoriteList: ArrayList<Int>? = this.intent.getIntegerArrayListExtra("filmId")
        Log.d("MyLOG", favoriteList.toString())

        val adapter = FilmAdapter({
            val intent = Intent(this@FavoriteActivity, FilmDescriptionActivity::class.java)
            intent.putExtra("filmId", it.filmId)
            startActivity(intent)},{
            favoriteList?.remove(it.filmId)
        })

        lifecycle.coroutineScope.launch {
            when (val films = NetworkService.getFilms().getOrNull()) {
                null -> {
                    adapter.submitList(listOf())
                    val intent = Intent(this@FavoriteActivity, ErrorActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    val favoriteFilmsList = mutableListOf<Film>()
                    for (film in films) {
                        if (film.filmId in favoriteList ?: emptyList()) {
                            favoriteFilmsList.add(film)
                        }
                    }
                    adapter.submitList(favoriteFilmsList)
                }
            }
        }
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rcView.adapter = adapter
        }


    }
}