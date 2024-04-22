package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.View
import android.widget.Adapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.FilmAdapter
import com.example.myapplication.databinding.ActivitySearchBinding
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    lateinit var adapter: FilmAdapter
    var loadFilms: List<Film>? = null
    var newFilms = listOf<Film>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        loadFilms()
        binding.ivEmpty.visibility = View.INVISIBLE

        setContentView(binding.root)

        val bundle = Bundle()
        adapter = FilmAdapter({
            val intentToFilmDescriptionActivity = Intent(this@SearchActivity, FilmDescriptionActivity::class.java)
            intentToFilmDescriptionActivity.putExtra("filmId", it.filmId)
            startActivity(intentToFilmDescriptionActivity)},{
            FavoriteListManager.addFilmToFavorites(it.filmId)
        }, false)
        update()
        binding.ibSearch.setOnClickListener {
            binding.apply {
                val userText = etSearch.text.toString()
                update(userText)
                Log.d("MyLog32", newFilms.size.toString())
                if (newFilms.isEmpty()) {
                    Log.d("MyLog33", newFilms.size.toString())
                    rcView.visibility = View.INVISIBLE
                    ivEmpty.visibility = View.VISIBLE
                } else {
                    Log.d("MyLog34", newFilms.size.toString())
                    ivEmpty.visibility = View.INVISIBLE
                    rcView.visibility = View.VISIBLE
                }
            }
        }

        binding.ibBack1.setOnClickListener {
            Log.d("MyLog", intent.getStringExtra("FromActivity").toString())
            if (intent.getStringExtra("FromActivity") == "MainActivity") {
                val intent = Intent(this@SearchActivity, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this@SearchActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
    }
    fun loadFilms() {
        lifecycle.coroutineScope.launch {
            when (val films = NetworkService.getFilms().getOrNull()) {
                null -> {
                    adapter.submitList(listOf())
                    val intent = Intent(this@SearchActivity, ErrorActivity::class.java)
                    startActivity(intent)
                }

                else -> {
                    loadFilms = films
                }
            }
        }
    }
    fun update(userText : String = "") {
        if (userText == ""){
            newFilms = loadFilms ?: listOf()
            adapter.submitList(newFilms)
        }
        else {
            Log.d("MyFAVLog", newFilms!!.filter{it.nameEn?.contains("Mon") ?: true}.toString() )
            newFilms = loadFilms!!.filter {
                it.nameRu?.contains(userText, ignoreCase = true)?: false
                        || (it.nameEn?.contains(userText, ignoreCase = true)?: false)
            }
            Log.d("MyLog3", newFilms.size.toString())
            adapter.submitList(newFilms)
        }

        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@SearchActivity)
            rcView.adapter = adapter
        }
    }
}