package com.dicoding.hairstyler.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.hairstyler.databinding.ActivityLoginBinding
import com.dicoding.hairstyler.ui.ViewModelFactory
import com.dicoding.hairstyler.ui.authentication.welcome.WelcomeActivity
import com.dicoding.hairstyler.ui.authentication.register.RegisterActivity
import com.dicoding.hairstyler.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var message: String = "Unknown Error"
    private val viewModel by viewModels<LoginViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnLoginAccount.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.loginUser(email, password)
                showLoading(true)
            }
        }

        binding.btnBackToWelcome.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        binding.btnToRegisterPage.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.errorMessage.observe(this) {errorMessage ->
            message = errorMessage
        }

        viewModel.status.observe(this) { success ->
            if (success) {
                showLoading(false)
                AlertDialog.Builder(this).apply {
                    setTitle("Success!")
                    setPositiveButton("CONTINUE") { _, _ ->
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            } else {
                showLoading(false)
                AlertDialog.Builder(this).apply {
                    setTitle("Failed!")
                    setMessage(message)
                    setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}