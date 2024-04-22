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
import okhttp3.internal.notify

class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    var listOfFavoriteFilms = listOf<Film>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bnPop.setOnClickListener{
            val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.ibSearch.setOnClickListener {
            val intent = Intent(this@FavoriteActivity, SearchActivity::class.java)
            intent.putExtra("FromActivity", "FavoriteActivity")
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()

        val adapter = FilmAdapter({
            val intent = Intent(this@FavoriteActivity, FilmDescriptionActivity::class.java)
            intent.putExtra("filmId", it.filmId)
            startActivity(intent)},{
                FavoriteListManager.removeFilmFromFavorites(it.filmId)
        },
            true)
        update(adapter)
        lifecycle.coroutineScope.launch {
            when (val films = NetworkService.getFilms().getOrNull()) {
                null -> {
                    adapter.submitList(listOf())
                    val intent = Intent(this@FavoriteActivity, ErrorActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    Log.d("MyLogLaunch", listOfFavoriteFilms.toString())
                    listOfFavoriteFilms = films
                    Log.d("MyLogFilterLog", listOfFavoriteFilms.toString())
                    update(adapter)
                }
            }
        }
    }
    fun update(adapter : FilmAdapter){
        adapter.submitList(listOfFavoriteFilms.filter { it.filmId in FavoriteListManager.getFavoriteList() ?: emptyList() })
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rcView.adapter = adapter
        }
    }
}