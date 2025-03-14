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

@Module
@InstallIn(ViewModelComponent::class)
abstract class CharacterModule {

    @Binds
    abstract fun apiService(apiService: CharacterApiService.Impl): CharacterApiService

    @Binds
    abstract fun cloudDataSource(datasource: CharacterCloudDataSource.Impl): CharacterCloudDataSource

    @Binds
    abstract fun cacheDataSource(dataSource: FavoriteCharacterCacheDataSource.Impl): FavoriteCharacterCacheDataSource

    @Binds
    abstract fun imageLoader(imageLoader: LoadImage.Impl): LoadImage

    @Binds
    abstract fun handleError(handleError: HandleError.Impl): HandleError

    @Binds
    abstract fun repository(repository: CharacterRepositoryImpl): CharacterRepository
}