package com.example.waveaccesstest.di

import com.example.waveaccesstest.api.CandidatesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideCandidatesAPI(): CandidatesAPI {
        return CandidatesAPI()
    }
}