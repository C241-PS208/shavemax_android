package com.dicoding.hairstyler.ui.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dicoding.hairstyler.R
import com.dicoding.hairstyler.databinding.FragmentDetailBinding
import com.dicoding.hairstyler.databinding.FragmentResultBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        val hairName = DetailFragmentArgs.fromBundle(arguments as Bundle).hairstyleName
        val hairDesc = DetailFragmentArgs.fromBundle(arguments as Bundle).hairstyleDescription
        val hairPic = DetailFragmentArgs.fromBundle(arguments as Bundle).hairstylePicture
        binding.tvHaircutDetailName.text = hairName
        binding.tvHaircutDetailDesc.text = hairDesc
        Glide.with(requireActivity()).load(hairPic).into(binding.ivDetailHairstylePicture)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}