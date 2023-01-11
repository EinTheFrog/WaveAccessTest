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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.waveaccesstest.databinding.FragmentHomeScreenBinding
import com.example.waveaccesstest.model.domain.Candidate
import com.example.waveaccesstest.ui.widgets.CandidateListAdapter
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

        val candidateList = mutableListOf<Candidate>()
        val candidateRecyclerAdapter = CandidateListAdapter(candidateList) { candidateId ->
            val navController = binding.candidatesRecycler.findNavController()
            val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToDetailsScreenFragment(candidateId = candidateId)
            navController.navigate(action)
        }
        binding.candidatesRecycler.adapter = candidateRecyclerAdapter
        binding.candidatesRecycler.layoutManager = LinearLayoutManager(context)

        val candidateListViewModel by viewModels<CandidateListViewModel>()
        candidateListViewModel.getCandidates()

        binding.swipeRefreshLayout.setOnRefreshListener {
            candidateListViewModel.syncCandidates()
        }

        lifecycleScope.launch {
            candidateListViewModel.candidateListState.collect { state ->
                val loading = state.isLoading
                val candidates = state.candidates

                binding.swipeRefreshLayout.isRefreshing = loading

                if (candidates.isNotEmpty()) {
                    candidateList.clear()
                    candidateList.addAll(candidates)
                    candidateRecyclerAdapter.notifyDataSetChanged()
                }
            }
        }

        return binding.root
    }
}