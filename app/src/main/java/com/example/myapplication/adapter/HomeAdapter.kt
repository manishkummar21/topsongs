package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemSongBinding
import com.example.myapplication.entities.SongWithImages

class HomeAdapter :
    androidx.recyclerview.widget.ListAdapter<SongWithImages, HomeAdapter.HomeViewHolder>(Companion) {

    class HomeViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    private var itemClickListener: ((SongWithImages) -> Unit)? = null


    companion object : DiffUtil.ItemCallback<SongWithImages>() {
        override fun areItemsTheSame(oldItem: SongWithImages, newItem: SongWithImages): Boolean =
            oldItem.song.id.equals(newItem.song.id)

        override fun areContentsTheSame(oldItem: SongWithImages, newItem: SongWithImages): Boolean =
            oldItem.song.id.equals(newItem.song.id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.song = item
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(item)
        }
    }

    fun setItemClickListener(listener: (item: SongWithImages) -> Unit) {
        itemClickListener = listener
    }

}