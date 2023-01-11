package com.example.waveaccesstest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.waveaccesstest.R
import com.example.waveaccesstest.databinding.FragmentHomeScreenBinding

class HomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FragmentHomeScreenBinding.inflate(inflater)
        binding.goToDetailsButton1.setOnClickListener{ view ->
            val navController = view.findNavController()
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailsScreenFragment(userId = 1)
            navController.navigate(action)
        }
        binding.goToDetailsButton2.setOnClickListener{ view ->
            val navController = view.findNavController()
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailsScreenFragment(userId = 2)
            navController.navigate(action)
        }

        return binding.root
    }
}