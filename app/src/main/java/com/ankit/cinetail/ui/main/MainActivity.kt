package com.ankit.cinetail.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ankit.cinetail.R
import com.ankit.cinetail.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // SAFELY get NavController from NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup bottom navigation with navController
        binding.bottomNavigation.setupWithNavController(navController)

        val result = "Hello from MainActivity"
        supportFragmentManager.setFragmentResult("home_request_key", Bundle().apply {
            putString("home_data_key", result)
        })


    }
}