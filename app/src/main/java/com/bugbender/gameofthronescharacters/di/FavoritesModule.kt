package com.bugbender.gameofthronescharacters.di

import com.bugbender.gameofthronescharacters.favorites.data.FavoriteCharactersRepositoryImpl
import com.bugbender.gameofthronescharacters.favorites.data.cache.FavoriteCharactersCacheDataSource
import com.bugbender.gameofthronescharacters.favorites.domain.FavoriteCharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoritesModule {

    @Binds
    @ViewModelScoped
    abstract fun cacheDataSource(cacheDataSource: FavoriteCharactersCacheDataSource.Impl): FavoriteCharactersCacheDataSource

    @Binds
    @ViewModelScoped
    abstract fun repository(repository: FavoriteCharactersRepositoryImpl): FavoriteCharactersRepository
}