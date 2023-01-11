package com.example.waveaccesstest.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waveaccesstest.model.domain.Candidate
import com.example.waveaccesstest.usecases.CandidatesUseCase
import com.example.waveaccesstest.usecases.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CANDIDATE_VIEW_MODEL_TAG = "CandidateViewModelTag"

data class CandidateListState(
    val candidates: List<Candidate>,
    val isLoading: Boolean
)

@HiltViewModel
class CandidateListViewModel @Inject constructor(
    private val candidatesUseCase: CandidatesUseCase
): ViewModel() {

    private val _candidateListState: MutableStateFlow<CandidateListState> = MutableStateFlow(
        CandidateListState(
            candidates = emptyList(),
            isLoading = false
        )
    )
    val candidateListState: StateFlow<CandidateListState> = _candidateListState

    fun getCandidates() {
        viewModelScope.launch {
            _candidateListState.update { state -> state.copy(isLoading = true) }

            getLocalCandidates()
            if (candidateListState.value.candidates.isEmpty()) {
                fetchCandidates()
            }

            _candidateListState.update { state -> state.copy(isLoading = false) }
        }
    }

    private suspend fun fetchCandidates() {
        val result = candidatesUseCase.fetchCandidates()
        when(result) {
            is Result.Success -> {
                _candidateListState.update { state -> state.copy(candidates = result.data) }
            }
            is Result.Error -> {
                Log.e(CANDIDATE_VIEW_MODEL_TAG, result.exception.toString(), result.exception)
            }
        }
    }

    private suspend fun getLocalCandidates() {
        val result = candidatesUseCase.getLocalCandidates()
        when(result) {
            is Result.Success -> {
                _candidateListState.update { state -> state.copy(candidates = result.data) }
            }
            is Result.Error -> {
                Log.e(CANDIDATE_VIEW_MODEL_TAG, result.exception.toString(), result.exception)
            }
        }
    }
}