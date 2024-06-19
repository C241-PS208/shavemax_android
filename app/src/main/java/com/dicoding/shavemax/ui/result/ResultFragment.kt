package com.dicoding.shavemax.ui.result

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.shavemax.R
import com.dicoding.shavemax.data.remote.response.ResultResponse
import com.dicoding.shavemax.databinding.FragmentResultBinding
import com.dicoding.shavemax.ui.ViewModelFactory
import com.dicoding.shavemax.ui.main.MainActivity

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val resultViewModel: ResultViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private val args: ResultFragmentArgs by navArgs()
    private lateinit var resultResponse: ResultResponse
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideBottomNav()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showBottomNav()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultResponse = args.ResultResponse
        imageUri = args.imageUri.toUri()

        setupAction()
        setupAdapter()
        setupView()
    }

    private fun setupView() {
        binding.tvFaceType.text = resultResponse.faceType
        binding.tvHairType.text = resultResponse.hairType
        binding.ivFace.setImageURI(imageUri)
    }

    private fun setupAction() {
        binding.btnBackToHome.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_navigation_home)
        }
    }

    private fun setupAdapter() {
        val resultAdapter = ResultAdapter()
        resultAdapter.submitList(resultResponse.recommendations ?: emptyList())
        binding.rvResult.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvResult.adapter = resultAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
