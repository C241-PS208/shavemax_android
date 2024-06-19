package com.dicoding.hairstyler.ui.savedhairstyle

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.hairstyler.data.local.room.SavedHairstyle
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.databinding.CardviewResultBinding

class SavedHairstyleAdapter : ListAdapter<SavedHairstyle, SavedHairstyleAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(val binding: CardviewResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SavedHairstyle) {
            binding.tvHaircutName.text = item.name
            binding.tvHaircutDesc.text = item.description
            Glide.with(binding.root)
                .load(item.photoUrl)
                .into(binding.ivResult)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CardviewResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val savedHairstyle = getItem(position)
        holder.bind(savedHairstyle)
        holder.itemView.setOnClickListener {
            val toDetailFragment = SavedHairstyleFragmentDirections.actionSavedHairstyleFragmentToDetailFragment(
                savedHairstyle.name,
                savedHairstyle.description,
                savedHairstyle.photoUrl
            )
            holder.itemView.findNavController().navigate(toDetailFragment)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SavedHairstyle>() {
            override fun areItemsTheSame(
                oldItem: SavedHairstyle,
                newItem: SavedHairstyle
            ): Boolean {
                return oldItem.name == newItem.name
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: SavedHairstyle,
                newItem: SavedHairstyle
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}