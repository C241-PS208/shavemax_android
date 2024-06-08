package com.dicoding.hairstyler.ui.scanner

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.dicoding.hairstyler.R
import com.dicoding.hairstyler.databinding.FragmentScannerBinding
import com.dicoding.hairstyler.utils.getImageUri

class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scannerViewModel =
            ViewModelProvider(this)[ScannerViewModel::class.java]

        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()

        binding.btnBackToHome.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_scanner_to_navigation_home)
        }
    }

    private fun setupAction() {
        binding.btnOpenCamera.setOnClickListener { openCamera() }
        binding.btnOpenGallery.setOnClickListener { openGallery() }
        binding.btnSubmitPicture.setOnClickListener { postPicture() }
    }

    private fun postPicture() {
        TODO("Not yet implemented")
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