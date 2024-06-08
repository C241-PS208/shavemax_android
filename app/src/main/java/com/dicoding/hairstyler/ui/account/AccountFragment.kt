package com.dicoding.hairstyler.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.hairstyler.databinding.FragmentAccountBinding
import com.dicoding.hairstyler.ui.ViewModelFactory
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val accountViewModel: AccountViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogOutAccount.setOnClickListener {
            accountViewModel.logOut()
        }

        accountViewModel.getUser().observe(viewLifecycleOwner){
            binding.name.text = it.name
            binding.gender.text = it.gender
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}