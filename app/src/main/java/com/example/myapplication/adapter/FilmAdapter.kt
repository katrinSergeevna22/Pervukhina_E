package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.Film
import com.example.myapplication.databinding.FilmItemBinding

class FilmAdapter(
    private val onInfoClicked: (Film) -> Unit,
    private val onLongClicked: (Film) -> Unit
) : ListAdapter<Film, FilmViewHolder>(FilmDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilmItemBinding.inflate(inflater, parent, false)
        return FilmViewHolder(binding, onInfoClicked, onLongClicked)
    }


    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.onBind(getItem(position))

    }

}