package com.example.starwars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.data.entity.Film
import com.example.starwars.databinding.AdapterFilmBinding

class FilmPagerAdapter :
    ListAdapter<Film, FilmPagerAdapter.FilmViewHolder>(
    FilmComparator
) {

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)!!
        holder.view.title.text = film.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterFilmBinding.inflate(inflater, parent, false)
        return FilmViewHolder(binding)
    }

    class FilmViewHolder(val view: AdapterFilmBinding): RecyclerView.ViewHolder(view.root)

    object FilmComparator: DiffUtil.ItemCallback<Film>() {
        override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
            return oldItem == newItem
        }
    }
}
