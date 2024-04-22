package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.FavoriteListManager
import com.example.myapplication.Film
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FilmItemBinding

class FilmAdapter(
    private val onInfoClicked: (Film) -> Unit,
    private val onLongClicked: (Film) -> Unit,
    private val isFavoriteActivity: Boolean,
) : ListAdapter<Film, FilmViewHolder>(FilmDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FilmItemBinding.inflate(inflater, parent, false)

        if (isFavoriteActivity) {
            binding.ivRaiting.visibility = View.VISIBLE
        } else {
            binding.ivRaiting.visibility = View.INVISIBLE
        }
        return FilmViewHolder(binding, onInfoClicked, onLongClicked)
    }


    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.onBind(getItem(position))

    }

}