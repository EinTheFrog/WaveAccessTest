package com.example.waveaccesstest.di

import com.example.waveaccesstest.api.CandidatesAPI
import com.example.waveaccesstest.model.mappers.CandidatesMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {
    @Singleton
    @Provides
    fun provideCandidatesMapper(): CandidatesMapper {
        return CandidatesMapper()
    }
}