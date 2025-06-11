package com.ankit.cinetail.ui.init

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ankit.cinetail.databinding.ActivitySignupBinding
import com.ankit.cinetail.databinding.ActivitySplashBinding
import com.ankit.cinetail.ui.auth.SignupActivity
import com.ankit.cinetail.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    companion object {
        private const val SPLASH_DELAY = 2000L // 2 seconds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        checkAuthStatus()
    }

    private fun setupObservers() {
        viewModel.authStatus.observe(this) { isAuthenticated ->
            Handler(Looper.getMainLooper()).postDelayed({
                navigateToNextScreen(isAuthenticated)
            }, SPLASH_DELAY)
        }
    }

    private fun checkAuthStatus() {
        lifecycleScope.launch {
            viewModel.checkAuthenticationStatus()
        }
    }

    private fun navigateToNextScreen(isAuthenticated: Boolean) {
//        val intent = if (isAuthenticated) {
//            Intent(this, MainActivity::class.java)
//        } else {
//            Intent(this, SignupActivity::class.java)
//        }
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
        finish()
    }
}