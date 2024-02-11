package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.FilmAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        var favoriteList = ArrayList<Int>()
        val bundle = Bundle()
        val adapter = FilmAdapter({
            val intent = Intent(this@MainActivity, FilmDescriptionActivity::class.java)
            intent.putExtra("filmId", it.filmId)
            startActivity(intent)},{
            val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
            intent.putExtra("filmId", it.filmId)
            favoriteList.add(it.filmId)
        }

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
            startActivity(intent)
        }
        binding.bnFavorite.setOnClickListener {

            val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
            intent.putExtra("", favoriteList)
            bundle.putIntegerArrayList("films", favoriteList)
            intent.putExtras(bundle)
            startActivity(intent)
        }


    }

}


