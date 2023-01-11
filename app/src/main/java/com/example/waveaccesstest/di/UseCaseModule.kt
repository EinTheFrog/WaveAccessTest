package com.example.waveaccesstest.di

import com.example.waveaccesstest.usecases.CandidatesUseCase
import com.example.waveaccesstest.usecases.CandidatesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindCandidatesUseCase(impl: CandidatesUseCaseImpl): CandidatesUseCase

}