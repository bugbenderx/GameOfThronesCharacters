package com.bugbender.gameofthronescharacters.di

import com.bugbender.gameofthronescharacters.core.ProvideStringRes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {

    @Binds
    @Singleton
    abstract fun provideStringRes(provideStringRes: ProvideStringRes.Impl): ProvideStringRes
}