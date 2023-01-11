package com.example.waveaccesstest.usecases

import com.example.waveaccesstest.api.CandidatesAPI
import com.example.waveaccesstest.cache.CandidatesDao
import com.example.waveaccesstest.model.domain.Candidate
import com.example.waveaccesstest.model.mappers.CandidatesMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CandidatesUseCase {
    suspend fun fetchCandidates(): Result<List<Candidate>>
    suspend fun getLocalCandidates(): Result<List<Candidate>>
}

class CandidatesUseCaseImpl @Inject constructor(
    private val candidatesDao: CandidatesDao,
    private val candidatesAPI: CandidatesAPI,
    private val candidatesMapper: CandidatesMapper
) : CandidatesUseCase {

    override suspend fun fetchCandidates(): Result<List<Candidate>> = withContext(Dispatchers.IO) {
        try {
            val dataCandidates = candidatesAPI.getCandidates()
            val cacheCandidates = dataCandidates.map { candidatesMapper.dataToCache(it) }
            candidatesDao.upsertCandidates(cacheCandidates)
            val domainCandidates = dataCandidates.map { candidatesMapper.dataToDomain(it) }
            return@withContext Result.Success(domainCandidates)
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun getLocalCandidates(): Result<List<Candidate>> = withContext(Dispatchers.IO) {
        try {
            val cacheCandidates = candidatesDao.getCandidates()
            val domainCandidates = cacheCandidates.map { candidatesMapper.cacheToDomain(it) }
            return@withContext Result.Success(domainCandidates)
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

}