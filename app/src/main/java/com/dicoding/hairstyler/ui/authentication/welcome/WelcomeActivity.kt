package com.dicoding.hairstyler.ui.authentication.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.hairstyler.databinding.ActivityWelcomeBinding
import com.dicoding.hairstyler.ui.ViewModelFactory
import com.dicoding.hairstyler.ui.authentication.login.LoginActivity
import com.dicoding.hairstyler.ui.authentication.register.RegisterActivity
import com.dicoding.hairstyler.ui.main.MainActivity
import com.dicoding.hairstyler.ui.main.MainViewModel

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val viewModel by viewModels<WelcomeViewModel> { ViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getToken().observe(this){
            if (it.token != ""){
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
            }
        }

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