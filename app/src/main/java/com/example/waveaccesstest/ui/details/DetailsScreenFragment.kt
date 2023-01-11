package com.example.waveaccesstest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.waveaccesstest.databinding.FragmentDetailsScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsScreenFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FragmentDetailsScreenBinding.inflate(inflater)

        val detailsScreenArgs by navArgs<DetailsScreenFragmentArgs>()
        val candidateId = detailsScreenArgs.candidateId

        binding.goToNextButton.setOnClickListener { view ->
            val navController = view.findNavController()
            val action = DetailsScreenFragmentDirections.actionDetailsScreenFragmentToDetailsScreenFragment(candidateId = candidateId + 1)
            navController.navigate(action)
        }

        val candidateDetailsViewModel by viewModels<CandidateDetailsViewModel>()
        candidateDetailsViewModel.getCandidateById(candidateId)
        lifecycleScope.launch {
            candidateDetailsViewModel.candidateState.collect { state ->
                val candidate = state.candidate
                binding.userDetailsText.text = "id: $candidateId, name: ${candidate?.name}"
            }
        }

        return binding.root
    }
}