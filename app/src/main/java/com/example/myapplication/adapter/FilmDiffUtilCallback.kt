package com.example.myapplication.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.Film

class FilmDiffUtilCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem.filmId == newItem.filmId

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean =
        oldItem == newItem
}