package com.example.storyappsubmission

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.storyappsubmission.presentation.ui.login.LoginActivity
import com.example.storyappsubmission.presentation.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var titleTextView: TextView
    private lateinit var descTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        imageView = findViewById(R.id.imageView)
        loginButton = findViewById(R.id.LoginButton)
        signUpButton = findViewById(R.id.SignUpButton)
        titleTextView = findViewById(R.id.titleTextView)
        descTextView = findViewById(R.id.descTextView)

        playAnimation()

        loginButton.setOnClickListener {
            Log.d("WelcomeActivity", "Login button clicked")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            Log.d("WelcomeActivity", "SignUp button clicked")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(loginButton, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(signUpButton, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(titleTextView, View.ALPHA, 1f).setDuration(100)
        val desc = ObjectAnimator.ofFloat(descTextView, View.ALPHA, 1f).setDuration(100)

        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }
        AnimatorSet().apply {
            playSequentially(title, desc, together)
            start()
        }
    }
}
