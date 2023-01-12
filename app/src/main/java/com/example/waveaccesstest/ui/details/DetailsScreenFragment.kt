package com.example.waveaccesstest.ui.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
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
import java.util.*
import java.util.jar.Manifest

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
           onCandidateClick(candidateId, binding)
        }
        binding.friendsRecycler.adapter = friendsRecyclerAdapter
        binding.friendsRecycler.layoutManager = LinearLayoutManager(context)

        val detailsScreenArgs by navArgs<DetailsScreenFragmentArgs>()
        val candidateId = detailsScreenArgs.candidateId

        val candidateDetailsViewModel by viewModels<CandidateDetailsViewModel>()
        candidateDetailsViewModel.getCandidateById(candidateId)


        subscribeToViewModelUpdates(
            candidateDetailsViewModel = candidateDetailsViewModel,
            binding = binding,
            friendsRecyclerAdapter = friendsRecyclerAdapter,
            friendsList = friendsList
        )

        binding.emailValue.setOnClickListener { startEmailActivity(candidateDetailsViewModel) }
        binding.phoneValue.setOnClickListener { startPhoneActivity(candidateDetailsViewModel) }
        binding.coordinatesValue.setOnClickListener { startMapActivity(candidateDetailsViewModel) }

        return binding.root
    }

    private fun subscribeToViewModelUpdates(
        candidateDetailsViewModel: CandidateDetailsViewModel,
        binding: FragmentDetailsScreenBinding,
        friendsRecyclerAdapter: CandidateListAdapter,
        friendsList: MutableList<Candidate>
    ) {
        lifecycleScope.launch {
            candidateDetailsViewModel.candidateState.collect { state ->
                val candidate = state.candidate
                if (candidate != null) {
                    updateUi(
                        candidate = candidate,
                        candidateFriends = state.candidateFriends,
                        binding = binding,
                        friendsRecyclerAdapter = friendsRecyclerAdapter,
                        friendsList = friendsList
                    )
                }
            }
        }
    }

    private fun onCandidateClick(candidateId: Long, binding: FragmentDetailsScreenBinding) {
        val navController = binding.friendsRecycler.findNavController()
        val action = DetailsScreenFragmentDirections
            .actionDetailsScreenFragmentToDetailsScreenFragment(candidateId = candidateId)
        navController.navigate(action)
    }

    private fun updateUi(
        candidate: Candidate,
        candidateFriends: List<Candidate>,
        binding: FragmentDetailsScreenBinding,
        friendsRecyclerAdapter: CandidateListAdapter,
        friendsList: MutableList<Candidate>
    ) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy")
        val formattedRegisteredDate = candidate.registered.format(formatter).toString()

        binding.nameText.text = candidate.name
        binding.registeredValue.text =formattedRegisteredDate
        binding.ageValue.text = candidate.age.toString()
        binding.companyValue.text = candidate.company
        binding.emailValue.text = candidate.email
        binding.phoneValue.text = candidate.phone
        binding.addressValue.text = candidate.address
        binding.coordinatesValue.text = "(${candidate.latitude}, ${candidate.longitude})"
        binding.eyeColorValue.background.setTint(getEyeColorTint(candidate.eyeColor))
        binding.favoriteFruitValue.text = getFruitEmoji(candidate.favoriteFruit)
        binding.aboutValue.text = candidate.about

        friendsList.clear()
        friendsList.addAll(candidateFriends)
        friendsRecyclerAdapter.notifyDataSetChanged()
    }

    private fun getEyeColorTint(eyeColor: EyeColor) = when(eyeColor) {
        EyeColor.BROWN -> ContextCompat.getColor(requireContext(), R.color.brown_eye)
        EyeColor.GREEN -> ContextCompat.getColor(requireContext(), R.color.green_eye)
        EyeColor.BLUE -> ContextCompat.getColor(requireContext(), R.color.blue_eye)
    }

    private fun getFruitEmoji(fruit: Fruit) = when(fruit) {
        Fruit.APPLE -> requireContext().getString(R.string.apple_emoji)
        Fruit.BANANA -> requireContext().getString(R.string.banana_emoji)
        Fruit.STRAWBERRY -> requireContext().getString(R.string.strawberry_emoji)
    }

    private fun startEmailActivity(viewModel: CandidateDetailsViewModel) {
        val candidate = viewModel.candidateState.value.candidate ?: return
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse(
            String.format(Locale.ENGLISH, "mailto:%s", candidate.email)
        )
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.no_email_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startPhoneActivity(viewModel: CandidateDetailsViewModel) {
        val candidate = viewModel.candidateState.value.candidate ?: return
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(
            String.format(Locale.ENGLISH, "tel:%s", candidate.phone)
        )
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.no_phone_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startMapActivity(viewModel: CandidateDetailsViewModel) {
        val candidate = viewModel.candidateState.value.candidate ?: return
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(
            String.format(Locale.ENGLISH, "geo:%f,%f", candidate.latitude, candidate.longitude)
        )
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.no_map_error, Toast.LENGTH_SHORT).show()
        }
    }
}