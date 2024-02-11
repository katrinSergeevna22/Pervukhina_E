package com.example.myapplication.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Film
import com.example.myapplication.R
import com.example.myapplication.databinding.FilmItemBinding
import com.squareup.picasso.Picasso

class FilmViewHolder(
    private val binding: FilmItemBinding,
    private val onInfoClicked: (Film) -> Unit,
    private val onLongClicked: (Film) -> Unit

) : RecyclerView.ViewHolder(binding.root) {
    private var _film: Film? = null
    private val film: Film
        get() = _film!!

    fun onBind(data: Film) {
        _film = data
        with(binding) {
            root.setOnClickListener {
                Log.d("My log: click", film.filmId.toString())
                onInfoClicked(film)
            }

            root.setOnLongClickListener{
                Log.d("My log: long click", film.filmId.toString())
                onLongClicked(film)
                false
            }

            tvTitle.text = film.nameRu
            tvTitleGenre.text = parseGenres(film.genres.toString()).capitalize() + " (" + film.year + ")"
            Log.d("gfh", film.genres.toString())

            Picasso.with(root.context)
                .load(film.posterUrl)
                .into(ivPoster)
        }
    }
    fun parseGenres(input: String): String {
        val pattern = Regex("genre=([a-zA-Zа-яА-Я]+)")
        val matches = pattern.findAll(input)

        val genresList = mutableListOf<String>()

        for (match in matches) {
            genresList.add(match.groupValues[1])
        }
        return genresList[0]
    }
}