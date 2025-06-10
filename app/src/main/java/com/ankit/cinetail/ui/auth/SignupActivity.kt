package com.ankit.cinetail.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ankit.cinetail.R
import com.ankit.cinetail.databinding.ActivitySignupBinding
import com.ankit.cinetail.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        observer()
    }

    private fun initView() {

        binding.googleAuthBtn.setOnClickListener {
            viewModel.signIn(this)
            Toast.makeText(this, "Google Signup Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.logOut.setOnClickListener {
            viewModel.logout()
        }

        binding.getUser.setOnClickListener {

            Toast.makeText(this, viewModel.getCurrentUser().toString(), Toast.LENGTH_SHORT).show()

        }
    }

    private fun observer() {
        viewModel.authState.observe(this) { user ->
            if (user != null) {
                // Update login status in DataStore
                lifecycleScope.launch {
                    viewModel.updateLoginStatus(true)
                    viewModel.setUserName(user.displayName.toString())
                }

                // Navigate to Home screen
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Update login status in DataStore
                lifecycleScope.launch {
                    viewModel.updateLoginStatus(false)
                }

                // Stay on login screen or show error
                Toast.makeText(this, "Not signed in", Toast.LENGTH_SHORT).show()
            }
        }
    }
}