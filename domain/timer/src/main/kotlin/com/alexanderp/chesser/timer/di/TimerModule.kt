package com.alexanderp.chesser.timer.di

import com.alexanderp.chesser.timer.repository.TimerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal object TimerModule {
    @Provides
    fun provideTimerRepository(): TimerRepository {
        return TimerRepository()
    }
}