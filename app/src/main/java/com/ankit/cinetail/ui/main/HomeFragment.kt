package com.ankit.cinetail.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ankit.cinetail.R
import com.ankit.cinetail.databinding.FragmentHomeBinding
import com.ankit.cinetail.ui.auth.SignupActivity

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        // Listen for data sent from MainActivity
        parentFragmentManager.setFragmentResultListener(
            "home_request_key",
            viewLifecycleOwner
        ) { _, bundle ->
            val message = bundle.getString("home_data_key")
            Log.d("HomeFragment", "Received message: $message")
            binding.textView.text = message  // Display it in the TextView
        }
    }

    private fun initView() {
        binding.textView.setOnClickListener {
            val intent = Intent(requireContext(), SignupActivity::class.java)
            startActivity(intent)
        }
    }

}