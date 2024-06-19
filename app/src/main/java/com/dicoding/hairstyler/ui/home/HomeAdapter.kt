package com.dicoding.hairstyler.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.databinding.CardviewResultBinding
import com.dicoding.hairstyler.ui.result.ResultFragmentDirections

class HomeAdapter : ListAdapter<HairstyleResponseItem, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(val binding: CardviewResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HairstyleResponseItem) {
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
        val hairstyleResponseItem = getItem(position)
        holder.bind(hairstyleResponseItem)
        holder.itemView.setOnClickListener {
            val toDetailFragment = ResultFragmentDirections.actionResultFragmentToDetailFragment(
                hairstyleResponseItem.name ?: "Tes Name",
                hairstyleResponseItem.description ?: "Tes Desc",
                hairstyleResponseItem.photoUrl ?: "https://cdn.britannica.com/61/137461-050-BB6C5D80/Brad-Pitt-2008.jpg"
            )
            holder.itemView.findNavController().navigate(toDetailFragment)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HairstyleResponseItem>() {
            override fun areItemsTheSame(
                oldItem: HairstyleResponseItem,
                newItem: HairstyleResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HairstyleResponseItem,
                newItem: HairstyleResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}