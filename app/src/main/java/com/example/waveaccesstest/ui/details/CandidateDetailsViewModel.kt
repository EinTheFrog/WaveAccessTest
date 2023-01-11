package com.example.waveaccesstest.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waveaccesstest.model.domain.Candidate
import com.example.waveaccesstest.usecases.CandidatesUseCase
import com.example.waveaccesstest.usecases.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CANDIDATE_VIEW_MODEL_TAG = "CandidateViewModelTag"

data class CandidateState(
    val candidate: Candidate?,
    val isLoading: Boolean
)

@HiltViewModel
class CandidateDetailsViewModel @Inject constructor(
    private val candidatesUseCase: CandidatesUseCase
): ViewModel() {

    private val _candidateState: MutableStateFlow<CandidateState> = MutableStateFlow(
        CandidateState(
            candidate = null,
            isLoading = false
        )
    )
    val candidateState: StateFlow<CandidateState> = _candidateState

    fun getCandidateById(candidateId: Long) {
        viewModelScope.launch {
            _candidateState.update { state -> state.copy(isLoading = true) }

            val result = candidatesUseCase.getLocalCandidateById(candidateId)
            when(result) {
                is Result.Success -> {
                    _candidateState.update { state -> state.copy(candidate = result.data) }
                }
                is Result.Error -> {
                    Log.e(CANDIDATE_VIEW_MODEL_TAG, result.exception.toString(), result.exception)
                }
            }

            _candidateState.update { state -> state.copy(isLoading = false) }
        }
    }
}