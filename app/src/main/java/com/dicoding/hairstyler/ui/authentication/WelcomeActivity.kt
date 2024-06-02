package com.dicoding.hairstyler.ui.authentication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.hairstyler.R
import com.dicoding.hairstyler.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnToLoginPage.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnToRegisterPage.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}