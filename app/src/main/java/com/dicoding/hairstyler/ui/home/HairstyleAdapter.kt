package com.dicoding.hairstyler.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.databinding.CardviewResultBinding
import com.dicoding.hairstyler.ui.result.ResultFragmentDirections

class HairstyleAdapter : ListAdapter<HairstyleResponseItem, HairstyleAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardviewResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aHairstyle = getItem(position)
        holder.bind(aHairstyle)
    }

    class MyViewHolder(val binding: CardviewResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aHairstyle: HairstyleResponseItem){
            Glide.with(binding.root.context)
                .load(aHairstyle.photoUrl)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.ivResult)
            binding.tvHaircutName.text = aHairstyle.name
            binding.tvHaircutDesc.text = aHairstyle.description

        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HairstyleResponseItem>() {
            override fun areItemsTheSame(oldItem: HairstyleResponseItem, newItem: HairstyleResponseItem): Boolean {
                return oldItem == newItem
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: HairstyleResponseItem, newItem: HairstyleResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}