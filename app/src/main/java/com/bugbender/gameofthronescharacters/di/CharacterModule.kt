package com.bugbender.gameofthronescharacters.di

import com.bugbender.gameofthronescharacters.character.data.CharacterRepositoryImpl
import com.bugbender.gameofthronescharacters.character.data.HandleError
import com.bugbender.gameofthronescharacters.character.data.LoadImage
import com.bugbender.gameofthronescharacters.character.data.cache.FavoriteCharacterCacheDataSource
import com.bugbender.gameofthronescharacters.character.data.cloud.CharacterApiService
import com.bugbender.gameofthronescharacters.character.data.cloud.CharacterCloudDataSource
import com.bugbender.gameofthronescharacters.character.domain.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class CharacterModule {

    @Binds
    @ViewModelScoped
    abstract fun apiService(apiService: CharacterApiService.Impl): CharacterApiService

    @Binds
    @ViewModelScoped
    abstract fun cloudDataSource(datasource: CharacterCloudDataSource.Impl): CharacterCloudDataSource

    @Binds
    @ViewModelScoped
    abstract fun cacheDataSource(dataSource: FavoriteCharacterCacheDataSource.Impl): FavoriteCharacterCacheDataSource

    @Binds
    @ViewModelScoped
    abstract fun imageLoader(imageLoader: LoadImage.Impl): LoadImage

    @Binds
    @ViewModelScoped
    abstract fun handleError(handleError: HandleError.Impl): HandleError

    @Binds
    @ViewModelScoped
    abstract fun repository(repository: CharacterRepositoryImpl): CharacterRepository
}