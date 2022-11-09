package com.example.starwars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.data.entity.Character
import com.example.starwars.databinding.AdapterCharacterBinding

class CharacterPagerAdapter(private val onClickListener: OnClickListener):
    PagingDataAdapter<Character, CharacterPagerAdapter.CharacterViewHolder>(
    CharacterComparator
) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)!!
        holder.view.name.text = character.name
        holder.itemView.setOnClickListener {
            onClickListener.onClick(character)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    class CharacterViewHolder(val view: AdapterCharacterBinding): RecyclerView.ViewHolder(view.root)

    object CharacterComparator: DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (character: Character) -> Unit) {
        fun onClick(character: Character) = clickListener(character)
    }
}
