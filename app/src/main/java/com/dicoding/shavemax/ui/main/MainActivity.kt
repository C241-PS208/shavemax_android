package com.dicoding.shavemax.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.shavemax.R
import com.dicoding.shavemax.databinding.ActivityMainBinding
import com.dicoding.shavemax.ui.ViewModelFactory
import com.dicoding.shavemax.ui.authentication.welcome.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getToken().observe(this){
            if (it.token == ""){
                startActivity(Intent(this, WelcomeActivity::class.java))
                finishAffinity()
            }
        }

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)


    }

    fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }

    fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }
}