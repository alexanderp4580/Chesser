package com.alexanderp.chesser.timer.di

import com.alexanderp.chesser.timer.controllers.GameTimerController
import com.alexanderp.chesser.timer.controllers.OneSecondTickController
import com.alexanderp.chesser.timer.repository.TimerConfigRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object TimerModule {
    @Provides
    fun provideTimerRepository(): TimerConfigRepository {
        return TimerConfigRepository()
    }

    @Provides
    @Singleton
    fun provideGameTimerController(
        oneSecondTickController: OneSecondTickController,
        coroutineDispatcher: CoroutineDispatcher
    ): GameTimerController {
        return GameTimerController(oneSecondTickController, coroutineDispatcher)
    }

    @Provides
    fun provideTickHandler(coroutineDispatcher: CoroutineDispatcher): OneSecondTickController {
        return OneSecondTickController(coroutineDispatcher)
    }

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}
