package com.dicoding.hairstyler.ui.savedhairstyle

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.hairstyler.R
import com.dicoding.hairstyler.databinding.FragmentSavedHairstyleBinding
import com.dicoding.hairstyler.ui.HairViewModelFactory
import com.dicoding.hairstyler.utils.ResultState

class SavedHairstyleFragment : Fragment() {

    private var _binding: FragmentSavedHairstyleBinding? = null

    private val viewModel: SavedHairstyleViewModel by viewModels {
        HairViewModelFactory.getInstance(requireActivity())
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedHairstyleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        setupAdapter()
    }

    fun setupAction() {
        binding.btnBackToHome.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun setupAdapter() {

        viewModel.savedHairstyle.observe(viewLifecycleOwner) { result ->
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
                        val homeAdapter = SavedHairstyleAdapter()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}