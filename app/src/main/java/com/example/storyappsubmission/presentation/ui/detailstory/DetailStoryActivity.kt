package com.example.storyappsubmission.presentation.ui.detailstory

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.storyappsubmission.databinding.ActivityDetailStoryBinding
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.presentation.viewmodel.DetailStoryViewModelImpl
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailStoryActivity : AppCompatActivity() {

    private val detailStoryViewModel: DetailStoryViewModelImpl by viewModel()

    private lateinit var ivStoryImage: ImageView
    private lateinit var tvStoryName: TextView
    private lateinit var tvEventSummary: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ivStoryImage = binding.ivStoryImage
        tvStoryName = binding.tvStoryName
        tvEventSummary = binding.tvEventSummary
        progressBar = binding.progressBar

        val storyId = intent.getStringExtra("STORY_ID") ?: ""
        Log.d("DetailStoryActivity", "Received story ID: $storyId")

        detailStoryViewModel.fetchDetailStory(storyId)

        detailStoryViewModel.detailStory.observe(this, Observer { result ->
            when (result) {
                is ResultUtil.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is ResultUtil.Success -> {
                    progressBar.visibility = View.GONE
                    result.data.story?.let { story ->
                        tvStoryName.text = story.name ?: "Nama tidak tersedia"
                        tvEventSummary.text = story.description ?: "Deskripsi tidak tersedia"
                        Glide.with(this)
                            .load(story.photoUrl)
                            .into(ivStoryImage)
                    } ?: showToast("Detail story tidak tersedia")
                }
                is ResultUtil.Error -> {
                    progressBar.visibility = View.GONE
                    showToast(result.message)
                }
            }
        })
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message ?: "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
    }
}
