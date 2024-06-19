package com.dicoding.shavemax.ui.news

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
import com.dicoding.shavemax.databinding.FragmentNewsBinding
import com.dicoding.shavemax.ui.ViewModelFactory
import com.dicoding.shavemax.ui.main.MainActivity
import com.dicoding.shavemax.utils.ResultState

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels{ ViewModelFactory.getInstance(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
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

        setupAction()
        setupAdapter()
    }

    private fun setupAdapter() {
        viewModel.getHairstyleNews().observe(viewLifecycleOwner){result ->
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
                        val newsList = result.data.articles
                        val newsAdapter = NewsAdapter()
                        newsAdapter.submitList(newsList)
                        binding.rvNews.layoutManager = LinearLayoutManager(requireActivity())
                        binding.rvNews.adapter = newsAdapter
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun setupAction() {
        binding.btnBackToHome.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}