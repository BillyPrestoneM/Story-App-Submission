package com.example.storyappsubmission.presentation.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.storyappsubmission.R
import com.example.storyappsubmission.di.utils.ResultUtil
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.storyappsubmission.presentation.viewmodel.AuthViewModelImpl
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val viewModel: AuthViewModelImpl by viewModel()

    private lateinit var imageView: ImageView
    private lateinit var signupButton: Button
    private lateinit var titleTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var passwordEditText: EditText



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        imageView = findViewById(R.id.imageView)
        signupButton = findViewById(R.id.signupButton)
        titleTextView = findViewById(R.id.titleTextView)
        nameTextView = findViewById(R.id.nameTextView)
        emailTextView = findViewById(R.id.emailTextView)
        passwordTextView = findViewById(R.id.passwordTextView)
        passwordEditText = findViewById(R.id.passwordEditText)

        setupListeners()
        observeViewModel()
        playAnimation()
        setMyButtonEnabled()

        passwordEditText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setMyButtonEnabled()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            }
        )
    }

    private fun setMyButtonEnabled() {
        val result = passwordEditText.text
        signupButton.isEnabled = result != null && result.toString().isNotEmpty()
    }

    private fun setupListeners() {
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val signupButton = findViewById<Button>(R.id.signupButton)

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            when {
                name.isEmpty() -> {
                    nameEditText.error = "Name is required"
                }

                email.isEmpty() -> {
                    emailEditText.error = "Email is required"
                }

                password.isEmpty() -> {
                    passwordEditText.error = "Password is required"
                }

                else -> {
                    nameEditText.error = null
                    emailEditText.error = null
                    passwordEditText.error = null
                    lifecycleScope.launch {
                        viewModel.register(name, email, password)
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val signupButton = findViewById<Button>(R.id.signupButton)

        lifecycleScope.launch {
            viewModel.user.collect { result ->
                when (result) {
                    is ResultUtil.Loading -> {
                        signupButton.isEnabled = false
                        progressBar.visibility = View.VISIBLE
                    }

                    is ResultUtil.Error -> {
                        signupButton.isEnabled = true
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, result.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                    }

                    is ResultUtil.Success -> {
                        signupButton.isEnabled = true
                        progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registration successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }

                    else -> {
                        signupButton.isEnabled = true
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE

        }.start()

        val register = ObjectAnimator.ofFloat(signupButton, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(titleTextView, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(nameTextView, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(emailTextView, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(passwordTextView, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(register, title, name, email, password)
        }

        AnimatorSet().apply {
            playSequentially(together)
            start()
        }
    }

}