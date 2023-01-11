package com.example.waveaccesstest.cache

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.waveaccesstest.model.cache.Candidate

@Dao
interface CandidatesDao {
    @Query("SELECT * FROM ${Candidate.TABLE_NAME} ORDER BY id ASC")
    suspend fun getCandidates(): List<Candidate>

    @Query("SELECT * FROM ${Candidate.TABLE_NAME} WHERE id = :candidateId")
    suspend fun getCandidateById(candidateId: Long): Candidate

    @Upsert(entity = Candidate::class)
    fun upsertCandidates(list: List<Candidate>)
}