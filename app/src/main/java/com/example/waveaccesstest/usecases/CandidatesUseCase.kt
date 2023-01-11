package com.example.waveaccesstest.usecases

import com.example.waveaccesstest.api.CandidatesAPI
import com.example.waveaccesstest.model.domain.Candidate
import com.example.waveaccesstest.model.mappers.CandidatesMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CandidatesUseCase {
    suspend fun updateCandidates(): Result<List<Candidate>>
    suspend fun getCandidates(): Result<List<Candidate>>
}

class CandidateUseCaseImpl @Inject constructor(
    private val candidatesAPI: CandidatesAPI,
    private val candidatesMapper: CandidatesMapper
) : CandidatesUseCase {

    override suspend fun updateCandidates(): Result<List<Candidate>> = withContext(Dispatchers.IO) {
        try {
            val dataCandidates = candidatesAPI.getCandidates()
            val domainCandidates = dataCandidates.map { candidatesMapper.dataToDomain(it) }
            return@withContext Result.Success(domainCandidates)
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun getCandidates(): Result<List<Candidate>> = withContext(Dispatchers.IO) {
        try {
            return@withContext Result.Success(emptyList())
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

}