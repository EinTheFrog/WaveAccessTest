package com.example.waveaccesstest.ui.home

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.waveaccesstest.databinding.FragmentHomeScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
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

        val candidateListViewModel by viewModels<CandidateListViewModel>()
        candidateListViewModel.getCandidates()

        lifecycleScope.launch {
            candidateListViewModel.candidateListState.collect { state ->
                val loading = state.isLoading
                val candidates = state.candidates

                if (loading) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.parentLayout.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.parentLayout.visibility = View.VISIBLE
                }

                if (candidates.isNotEmpty()) {
                    binding.greetingText.text = "Hello ${candidates[0].name}!"
                }
            }
        }

        return binding.root
    }
}