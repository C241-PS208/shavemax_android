package com.dicoding.hairstyler.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dicoding.hairstyler.R
import com.dicoding.hairstyler.databinding.FragmentDetailBinding
import com.dicoding.hairstyler.ui.HairViewModelFactory

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels {
        HairViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.btnBackToResult.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupView() {
        val hairName = DetailFragmentArgs.fromBundle(arguments as Bundle).hairstyleName
        val hairDesc = DetailFragmentArgs.fromBundle(arguments as Bundle).hairstyleDescription
        val hairPic = DetailFragmentArgs.fromBundle(arguments as Bundle).hairstylePicture
        binding.tvHaircutDetailName.text = hairName
        binding.tvHaircutDetailDesc.text = hairDesc
        Glide.with(requireActivity()).load(hairPic).into(binding.ivDetailHairstylePicture)

        detailViewModel.checkSaved(hairName).observe(viewLifecycleOwner) { isSaved ->
            if (isSaved != null) {
                binding.fab.setImageResource(R.drawable.baseline_favorite_24)
                binding.fab.setOnClickListener {
                    detailViewModel.deleteHairstyle(hairName, hairDesc, hairPic)
                }
            }
            else {
                binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
                binding.fab.setOnClickListener {
                    detailViewModel.saveHairstyle(hairName, hairDesc, hairPic)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}