package com.dicoding.hairstyler.ui.result

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.hairstyler.R
import com.dicoding.hairstyler.databinding.FragmentResultBinding
import com.dicoding.hairstyler.databinding.FragmentScannerBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackToHome.setOnClickListener {
            it.findNavController().navigate(R.id.action_resultFragment_to_navigation_home)
        }

        setupAdapter()
    }

    private fun setupAdapter() {
        val resultAdapter = ResultAdapter()
        //Submit data
        binding.rvResult.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvResult.adapter = resultAdapter
    }
}