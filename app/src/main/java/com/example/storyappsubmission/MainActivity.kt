package com.example.storyappsubmission

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyappsubmission.data.storage.AuthPreferencesToken
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.presentation.adapter.StoryAdapter
import com.example.storyappsubmission.presentation.ui.addstory.AddStoryActivity
import com.example.storyappsubmission.presentation.ui.login.LoginActivity
import com.example.storyappsubmission.presentation.viewmodel.MainViewModelImpl
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModelImpl by viewModel()
    private val authPreference: AuthPreferencesToken by inject()
    private lateinit var adapter: StoryAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        val recyclerView = findViewById<RecyclerView>(R.id.rvStory)

        adapter = StoryAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        observeViewModel()
        checkAuthentication()

        val ivAddStory = findViewById<ImageView>(R.id.ivAddStory)
        ivAddStory.setOnClickListener {
            try {
                val intent = Intent(this, AddStoryActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("MainActivity", "Gagal membuka AddStoryActivity: ${e.message}")
                Toast.makeText(this, "Gagal membuka halaman Add Story", Toast.LENGTH_SHORT).show()
            }
        }

        val ivLogout = findViewById<ImageView>(R.id.ivLogout)
        ivLogout.setOnClickListener {
            lifecycleScope.launch {
                try {
                    authPreference.clearSession()
                    Toast.makeText(this@MainActivity, "Logout berhasil", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                } catch (e: Exception) {
                    Log.e("MainActivity", "Gagal logout: ${e.message}")
                    Toast.makeText(this@MainActivity, "Gagal logout", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun checkAuthentication() {
        lifecycleScope.launch {
            val token = authPreference.getToken()
            if (token.isNullOrEmpty()) {
                navigateToLogin()
            }else {
                viewModel.fetchStories()
            }
        }
    }

    private fun navigateToLogin() {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.storyList.observe(this@MainActivity) { result ->
                when (result) {
                    is ResultUtil.Success -> {
                        progressBar.visibility = View.GONE
                        adapter.submitList(result.data)
                    }

                    is ResultUtil.Error -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, result.message, Toast.LENGTH_SHORT).show()
                    }

                    is ResultUtil.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }

                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val token = authPreference.getToken()
            if (!token.isNullOrEmpty()) {
                viewModel.fetchStories()
            }
        }
    }
}