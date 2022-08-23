package com.cren90.android.logging.di

import com.cren90.android.logging.Logger
import com.cren90.android.logging.ModularLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
class LoggingModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger = ModularLogger()
}