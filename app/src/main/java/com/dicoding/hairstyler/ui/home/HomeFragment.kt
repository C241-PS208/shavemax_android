package com.dicoding.hairstyler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.hairstyler.databinding.FragmentHomeBinding
import com.dicoding.hairstyler.ui.HairViewModelFactory
import com.dicoding.hairstyler.utils.ResultState

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        HairViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getUser().observe(viewLifecycleOwner){
            binding.tvDisplayUsername.text = it.name
        }

        setupAction()
        setupAdapter()
    }

    private fun setupAdapter() {
        homeViewModel.hairList.observe(viewLifecycleOwner){result ->
            if (result != null){
                when (result) {
                    is ResultState.Error -> {
                        showLoading(false)
                        AlertDialog.Builder(requireActivity()).apply {
                            setTitle("Failed!")
                            setMessage(result.error)
                            setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            create()
                            show()
                        }
                    }
                    is ResultState.Loading -> showLoading(true)

                    is ResultState.Success -> {
                        showLoading(false)
                        val hairstyleList = result.data
                        val homeAdapter = HomeAdapter()
                        homeAdapter.submitList(hairstyleList)
                        binding.rvResult.layoutManager = LinearLayoutManager(requireActivity())
                        binding.rvResult.adapter = homeAdapter
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.isVisible = isLoading
        }
    }

    private fun setupAction() {
        binding.btnOpenScanner.setOnClickListener{
            val toScannerFragment = HomeFragmentDirections.actionNavigationHomeToNavigationScanner()
            it.findNavController().navigate(toScannerFragment)
        }
        binding.btnOpenNews.setOnClickListener {
            val toNewsFragment =  HomeFragmentDirections.actionNavigationHomeToNewsFragment()
            it.findNavController().navigate(toNewsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}