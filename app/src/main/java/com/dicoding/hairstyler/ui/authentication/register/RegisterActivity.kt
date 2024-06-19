package com.dicoding.hairstyler.ui.authentication.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.hairstyler.databinding.ActivityRegisterBinding
import com.dicoding.hairstyler.ui.ViewModelFactory
import com.dicoding.hairstyler.ui.authentication.login.LoginActivity
import com.dicoding.hairstyler.ui.authentication.welcome.WelcomeActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var selectedGender: String = "Male" // Default Value
    private var message: String = "Unknown Error"
    private val viewModel by viewModels<RegisterViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnCreateAccount.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val name = binding.edtName.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.registerUser(name, email, password, selectedGender)
                showLoading(true)
            }
        }

        binding.btnBackToWelcome.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        binding.btnToLoginPage.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        setupSpinner()
        setupViewModel()
    }

    private fun setupSpinner() {
        val genderOptions = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter

        binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedGender = genderOptions[position]
                Log.d(TAG, "selectedGender: $selectedGender")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
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

    companion object {
        private const val TAG = "RegisterActivity"
    }
}