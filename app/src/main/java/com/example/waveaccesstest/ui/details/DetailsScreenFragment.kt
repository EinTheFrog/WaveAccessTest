package com.example.waveaccesstest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waveaccesstest.R
import com.example.waveaccesstest.databinding.FragmentDetailsScreenBinding
import com.example.waveaccesstest.model.domain.Candidate
import com.example.waveaccesstest.model.domain.EyeColor
import com.example.waveaccesstest.model.domain.Fruit
import com.example.waveaccesstest.ui.home.HomeScreenFragmentDirections
import com.example.waveaccesstest.ui.widgets.CandidateListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DetailsScreenFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FragmentDetailsScreenBinding.inflate(inflater)

        val friendsList = mutableListOf<Candidate>()
        val friendsRecyclerAdapter = CandidateListAdapter(friendsList) { candidateId ->
            val navController = binding.friendsRecycler.findNavController()
            val action = DetailsScreenFragmentDirections
                .actionDetailsScreenFragmentToDetailsScreenFragment(candidateId = candidateId)
            navController.navigate(action)
        }
        binding.friendsRecycler.adapter = friendsRecyclerAdapter
        binding.friendsRecycler.layoutManager = LinearLayoutManager(context)

        val detailsScreenArgs by navArgs<DetailsScreenFragmentArgs>()
        val candidateId = detailsScreenArgs.candidateId

        val candidateDetailsViewModel by viewModels<CandidateDetailsViewModel>()
        candidateDetailsViewModel.getCandidateById(candidateId)

        lifecycleScope.launch {
            candidateDetailsViewModel.candidateState.collect { state ->
                val candidate = state.candidate
                if (candidate != null) {
                    val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy")
                    val formattedRegisteredDate = candidate.registered.format(formatter).toString()

                    binding.nameText.text = candidate.name
                    binding.registeredValue.text =formattedRegisteredDate
                    binding.ageValue.text = candidate.age.toString()
                    binding.companyValue.text = candidate.company
                    binding.emailValue.text = candidate.email
                    binding.phoneValue.text = candidate.phone
                    binding.addressValue.text = candidate.address
                    binding.eyeColorValue.background = when(candidate.eyeColor) {
                        EyeColor.BROWN -> AppCompatResources.getDrawable(requireContext(), R.drawable.brown_eye)
                        EyeColor.GREEN -> AppCompatResources.getDrawable(requireContext(), R.drawable.green_eye)
                        EyeColor.BLUE -> AppCompatResources.getDrawable(requireContext(), R.drawable.blue_eye)
                    }
                    binding.favoriteFruitValue.text = when(candidate.favoriteFruit) {
                        Fruit.APPLE -> requireContext().getString(R.string.apple_emoji)
                        Fruit.BANANA -> requireContext().getString(R.string.banana_emoji)
                        Fruit.STRAWBERRY -> requireContext().getString(R.string.strawberry_emoji)
                    }
                    binding.aboutValue.text = candidate.about

                    friendsList.clear()
                    friendsList.addAll(state.candidateFriends)
                    friendsRecyclerAdapter.notifyDataSetChanged()
                }
            }
        }

        return binding.root
    }
}