package com.example.storyappsubmission.presentation.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.storyappsubmission.MainActivity
import com.example.storyappsubmission.R
import com.example.storyappsubmission.di.utils.ResultUtil
import com.example.storyappsubmission.presentation.viewmodel.AuthViewModelImpl
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: AuthViewModelImpl by viewModel()

    private lateinit var imageView: ImageView
    private lateinit var loginButton: Button
    private lateinit var titleTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordEditTextLayout: com.google.android.material.textfield.TextInputLayout
    private lateinit var emailEditTextLayout: com.google.android.material.textfield.TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.fetchToken()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.token.collect { token ->
                    if (token != null) {
                        navigateToMain()
                    }
                }
            }
        }


        imageView = findViewById(R.id.imageView)
        loginButton = findViewById(R.id.loginButton)
        messageTextView = findViewById(R.id.messageTextView)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordEditTextLayout = findViewById(R.id.passwordEditTextLayout)
        emailEditTextLayout = findViewById(R.id.emailEditTextLayout)
        titleTextView = findViewById(R.id.titleTextView)

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
        loginButton.isEnabled = result != null && result.toString().isNotEmpty()
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun setupListeners() {
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            when {
                email.isEmpty() -> {
                    emailEditText.error = "Email is required"
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailEditText.error = "Invalid email format"
                }
                password.isEmpty() -> {
                    passwordEditText.error = "Password is required"
                }
                else -> {
                    emailEditText.error = null
                    passwordEditText.error = null
                    lifecycleScope.launch {
                        viewModel.login(email, password)
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val loginButton = findViewById<Button>(R.id.loginButton)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.user.collect { result ->
                    when (result) {
                        is ResultUtil.Loading -> {
                            loginButton.isEnabled = false
                            progressBar.visibility = View.VISIBLE
                        }
                        is ResultUtil.Error -> {
                            loginButton.isEnabled = true
                            progressBar.visibility = View.GONE
                            Toast.makeText(this@LoginActivity, result.message, Toast.LENGTH_SHORT).show()
                        }
                        is ResultUtil.Success -> {
                            loginButton.isEnabled = true
                            progressBar.visibility = View.GONE
                            navigateToMain()
                        }
                        else -> {
                            loginButton.isEnabled = true
                            progressBar.visibility = View.GONE
                        }
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

        val login = ObjectAnimator.ofFloat(loginButton, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(titleTextView, View.ALPHA, 1f).setDuration(100)
        val message = ObjectAnimator.ofFloat(messageTextView, View.ALPHA, 1f).setDuration(100)
        val email = ObjectAnimator.ofFloat(emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val password = ObjectAnimator.ofFloat(passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)

        val together = AnimatorSet().apply {
            playTogether(login)
        }

        AnimatorSet().apply {
            playSequentially(title, message, email, password, login, together)
            start()
        }
    }
}
