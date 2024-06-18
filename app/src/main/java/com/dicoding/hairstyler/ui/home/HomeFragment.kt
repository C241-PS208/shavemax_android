package com.dicoding.hairstyler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.hairstyler.data.remote.response.HairstyleResponse
import com.dicoding.hairstyler.data.remote.response.HairstyleResponseItem
import com.dicoding.hairstyler.data.remote.response.ResultResponse
import com.dicoding.hairstyler.databinding.FragmentHomeBinding
import com.dicoding.hairstyler.ui.HairViewModelFactory
import com.dicoding.hairstyler.ui.ViewModelFactory
import com.dicoding.hairstyler.ui.scanner.ScannerFragmentDirections
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
        homeViewModel.listHairstyle.observe(viewLifecycleOwner) { hairstyles ->
            setupAdapter(hairstyles)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        homeViewModel.getHairstyles()
    }

    private fun setupAction() {
        binding.btnOpenScanner.setOnClickListener{
            val toScannerFragment = HomeFragmentDirections.actionNavigationHomeToNavigationScanner()
            it.findNavController().navigate(toScannerFragment)
        }
    }

    private fun setupAdapter(listHairstyle: List<HairstyleResponseItem>) {
        val adapter = HairstyleAdapter()
        adapter.submitList(listHairstyle)
        binding.rvResult.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvResult.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}