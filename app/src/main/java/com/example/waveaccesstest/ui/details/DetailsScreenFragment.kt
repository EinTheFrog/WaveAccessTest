package com.example.waveaccesstest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.waveaccesstest.databinding.FragmentDetailsScreenBinding

class DetailsScreenFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FragmentDetailsScreenBinding.inflate(inflater)
        val detailsScreenArgs by navArgs<DetailsScreenFragmentArgs>()
        val userId = detailsScreenArgs.userId
        binding.userDetailsText.text = "Screen for user with id: $userId"
        binding.goToNextButton.setOnClickListener { view ->
            val navController = view.findNavController()
            val action = DetailsScreenFragmentDirections.actionDetailsScreenFragmentToDetailsScreenFragment(userId = userId + 1)
            navController.navigate(action)
        }

        return binding.root
    }
}