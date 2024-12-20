package com.example.storyappsubmission.presentation.ui.addstory

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.storyappsubmission.databinding.ActivityAddStoryBinding
import com.example.storyappsubmission.domain.usecase.UploadStoryUseCase
import com.example.storyappsubmission.presentation.viewmodel.UploadStoryViewModelImpl
import org.koin.android.ext.android.get

class AddStoryActivity : AppCompatActivity() {
    private val uploadStoryViewModel: UploadStoryViewModelImpl by viewModels {
        UploadStoryViewModelImpl.Factory(get<UploadStoryUseCase>())
    }

    private lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        uploadStoryViewModel.isLoading.observe(this) {isLoading ->
            showLoading(isLoading)
        }
        uploadStoryViewModel.errorMessage.observe(this) { message ->
            message?.let { showToast(it) }
        }
        uploadStoryViewModel.successMessage.observe(this) { message ->
            message?.let { showToast(it) }
        }

        uploadStoryViewModel.navigateToMain.observe(this) { navigate ->
            if (navigate) {
                finish()
            }
        }

        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.galleryButton.setOnClickListener {
            startGallery()
        }
        binding.cameraButton.setOnClickListener {
            startCamera()
        }
        binding.uploadButton.setOnClickListener {
            uploadImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
    }

    private fun uploadImage() {
            val description = binding.etDescription.text.toString()
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, this).reduceFileImage()
                uploadStoryViewModel.uploadStoryModel(description, imageFile)
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
