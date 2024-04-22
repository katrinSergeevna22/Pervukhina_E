package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.FilmAdapter
import com.example.myapplication.adapter.FilmViewHolder
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //favoriteList = intent.getIntegerArrayListExtra("favoriteFilms") ?: ArrayList<Int>()
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        val bundle = Bundle()
        val adapter = FilmAdapter({
            val intent = Intent(this@MainActivity, FilmDescriptionActivity::class.java)
            intent.putExtra("filmId", it.filmId)
            startActivity(intent)},{
            FavoriteListManager.addFilmToFavorites(it.filmId)
        },
            false
        )
        lifecycle.coroutineScope.launch {
            when (val films = NetworkService.getFilms().getOrNull()) {
                null -> {
                    adapter.submitList(listOf())
                    val intent = Intent(this@MainActivity, ErrorActivity::class.java)
                    startActivity(intent)
                }
                else -> adapter.submitList(films)
            }
        }
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@MainActivity)
            rcView.adapter = adapter
        }
        binding.ibSearch.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            intent.putExtra("FromActivity", "MainActivity")
            startActivity(intent)
        }
        binding.bnFavorite.setOnClickListener {
            val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
            //intent.putExtras(bundle)
            startActivity(intent)
        }

    }

}


