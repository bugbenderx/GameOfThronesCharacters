package com.bugbender.gameofthronescharacters.di

import android.content.Context
import androidx.room.Room
import com.bugbender.gameofthronescharacters.core.data.cache.FavoriteCharactersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FavoriteCharactersDatabase =
        Room.databaseBuilder(
            context,
            FavoriteCharactersDatabase::class.java,
            "favorite_characters_db"
        ).build()

    @Provides
    fun provideCharacterDao(db: FavoriteCharactersDatabase) = db.characterDao()
}