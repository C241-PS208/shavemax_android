package com.dicoding.hairstyler.ui.scanner

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dicoding.hairstyler.R
import com.dicoding.hairstyler.data.remote.response.ResultResponse
import com.dicoding.hairstyler.databinding.FragmentScannerBinding
import com.dicoding.hairstyler.ui.ViewModelFactory
import com.dicoding.hairstyler.ui.main.MainActivity
import com.dicoding.hairstyler.utils.ResultState
import com.dicoding.hairstyler.utils.getImageUri
import com.dicoding.hairstyler.utils.uriToFile

class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private var gender : String? = null

    private val viewModel : ScannerViewModel by viewModels { ViewModelFactory.getInstance(requireActivity()) }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideBottomNav()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).showBottomNav()
    }

    private fun setupAction() {
        binding.btnBackToHome.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_scanner_to_navigation_home)
        }
        binding.btnOpenCamera.setOnClickListener { openCamera() }
        binding.btnOpenGallery.setOnClickListener { openGallery() }
        binding.btnSubmitPicture.setOnClickListener {
            postPicture()
        }
    }

    private fun postPicture() {
        currentImageUri?.let { uri ->
            val image = uriToFile(uri, requireActivity())
            viewModel.getUser().observe(viewLifecycleOwner){
                gender = it.gender.lowercase()
            }
            viewModel.predict(image, gender ?: "male").observe(viewLifecycleOwner){
                handleResult(it)
            }
        }
    }

    private fun handleResult(result: ResultState<ResultResponse>?) {
        if (result != null){
            when (result){
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
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    showLoading(false)
                    AlertDialog.Builder(requireActivity()).apply {
                        setTitle("Scan Success!")
                        setPositiveButton("CONTINUE") { _, _ ->
                            val resultResponse = result.data
                            val imageUri = currentImageUri.toString()
                            val toResultFragment = ScannerFragmentDirections.actionNavigationScannerToResultFragment(resultResponse, imageUri)
                            findNavController().navigate(toResultFragment)
                        }
                        create()
                        show()
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.isVisible = isLoading
            btnSubmitPicture.isInvisible = isLoading
            btnOpenCamera.isInvisible = isLoading
            btnOpenGallery.isInvisible = isLoading
        }
    }

    private fun openGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun openCamera() {
        currentImageUri = getImageUri(requireActivity())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivFace.setImageURI(it)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", getString(R.string.no_media_selected))
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}